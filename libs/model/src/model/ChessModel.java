package model;

import api.model.IChessModel;
import api.model.Square;
import api.model.State;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class ChessModel implements IChessModel {

	private final Semaphore semaphore = new Semaphore(1);
	private String errorMessage;
	private Thread moveThread;
	private Thread drawOfferThread;
	private IReader<State> stateReader;
	private IWriter<State> stateWriter;
	private BlockingQueue<State> stateQueue;
	private IReader<String> reader;
	private IWriter<String> writer;
	private BlockingQueue<String> dataQueue;
	private IReader<Square[]> moveReader;
	private IWriter<Square[]> moveWriter;
	private BlockingQueue<Square[]> moveQueue;
	private IBoard board;
	private IMoveList moveList;

	public ChessModel() {
		setStateQueue(new LinkedBlockingQueue<>());
		setStateReader(getStateQueue());
		setStateWriter(getStateQueue());
		setDataQueue(new LinkedBlockingQueue<>());
		setReader(getDataQueue());
		setWriter(getDataQueue());
		setMoveQueue(new LinkedBlockingQueue<>());
		setMoveReader(getMoveQueue());
		setMoveWriter(getMoveQueue());
		initMoveThread();
		initDrawOfferThread();
	}

	public State getGameState() {
		return getStateReader().peek();
	}

	public String getMoves() {
		return getMoveList().toString();
	}

	/**
	 * @return String[] getPiecePlacementData
	 */
	public String[] getPiecePlacementData() {
		return getBoard().getFen().getPiecePlacementData();
	}

	/**
	 * @return boolean isWhite
	 */
	public boolean isWhite() {
		return getBoard().getFen().isWhite();
	}

	/**
	 * @return String castlingInformation
	 */
	public String getCastling() {
		return getBoard().getFen().getCastling();
	}

	/**
	 * @return boolean canWhiteKingCastle
	 */
	public boolean getWhiteKingCastle() {
		return getBoard().getFen().getWhiteKingCastle();
	}

	/**
	 * @return boolean canWhiteQueenCastle
	 */
	public boolean getWhiteQueenCastle() {
		return getBoard().getFen().getWhiteQueenCastle();
	}

	/**
	 * @return boolean canBlackKingCastle
	 */
	public boolean getBlackKingCastle() {
		return getBoard().getFen().getWhiteKingCastle();
	}

	/**
	 * @return boolean canBlackQueenCastle
	 */
	public boolean getBlackQueenCastle() {
		return getBoard().getFen().getBlackKingCastle();
	}

	/**
	 * @return boolean enPassantSquare
	 */
	public String getEnPassant() {
		return getBoard().getFen().getEnPassant();
	}

	/**
	 * @return int halfMoveClock
	 */
	public int getHalfMoveClock() {
		return getBoard().getFen().getHalfMoveClock();
	}

	/**
	 * @return int fullMoveNumber
	 */
	public int getFullMoveNumber() {
		return getBoard().getFen().getFullMoveNumber();
	}

	public String getFen() {
		return getBoard().getFen().toString();
	}

	public void startGame() {
		setBoard(new Board());
		setMoveList(new MoveList());
		setGameState(State.Start);
		printGame();
	}

	public void startNewGame() {
		acquire();
		startGame();
		setGameState(State.Playing);
		initMoveThread();
		getMoveThread().start();
	}

	public void makeMove(Square source, Square destination) {
		getMoveWriter().write(new Square[] { source, destination });
	}

	/**
	 * @param square The square to check for own piece
	 * @return boolean Whether own piece is on the given square
	 */
	public boolean isOwnPieceOnSquare(Square square) {
		IBitboard piece = Bitboard.getSingleBit(Board.getIndex(square));
		IBitboard ownPieces = getBoard().getPieces(getBoard().getFen().isWhite());
		return Bitboard.overlap(piece, ownPieces);
	}

	public void resign() {
		System.out.println("Resigned");
		acquire();
		setGameState(State.Resignation);
		release();
	}

	public void offerDraw() {
		if (getDrawOfferThread().getState() == Thread.State.NEW) {
			getDrawOfferThread().start();
		} else {
			getDrawOfferThread().interrupt();
			initDrawOfferThread();
			getDrawOfferThread().start();
		}
	}

	public void acceptDraw() {
		getWriter().write("Y");
	}

	public void declineDraw() {
		getWriter().write("N");
	}

	public void claimDraw() {
		System.out.println("Draw claimed");
		acquire();
		setGameState(State.Draw);
		release();
	}

	public ArrayList<Square[]> getLegalMoves(Square square) {
		IPiece piece = getBoard().getPiece(square);
		ArrayList<Square[]> allMoves = piece.getPieceMoves(
			getBoard(),
			Bitboard.getSingleBit(Board.getIndex(square))
		);
		return getLegalMoves(allMoves);
	}

	public void promoteQueen() {
		getWriter().write("Q");
	}

	public void promoteRook() {
		getWriter().write("R");
	}

	public void promoteKnight() {
		getWriter().write("N");
	}

	public void promoteBishop() {
		getWriter().write("B");
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void clearError() {
		setErrorMessage(null);
	}

	Thread getMoveThread() {
		return moveThread;
	}

	IBoard getBoard() {
		return board;
	}

	IMoveList getMoveList() {
		return moveList;
	}

	void startGame(String fen) {
		setBoard(new Board(new ForsythEdwardsNotation(fen)));
		setMoveList(new MoveList());
		setGameState(State.Start);
		printGame();
	}

	void gameOver() {
		setGameState(State.Start);
	}

	boolean isCheckmate() {
		return isCheck() && hasNoLegalMoves();
	}

	boolean isStalemate() {
		return !isCheck() && hasNoLegalMoves();
	}

	void acquire() {
		try {
			getSemaphore().acquire();
		} catch (InterruptedException ignored) {}
	}

	void release() {
		getSemaphore().release();
	}

	private ArrayList<Square[]> getAllLegalMoves() {
		ArrayList<Square[]> allMoves = getBoard().getAllMoves(getBoard().getFen().isWhite());
		return getLegalMoves(allMoves);
	}

	private ArrayList<Square[]> getLegalMoves(ArrayList<Square[]> allMoves) {
		ArrayList<Square[]> legalMoves = new ArrayList<>();
		for (Square[] move : allMoves) {
			try {
				int src = Board.getIndex(move[0]);
				int dst = Board.getIndex(move[1]);
				boolean isIllegal = getBoard()
					.getPiece(move[0])
					.isInvalidMove(src, dst, getBoard());
				if (!isIllegal) {
					legalMoves.add(new Square[] { move[0], move[1] });
				}
			} catch (Error ignored) {}
		}
		return legalMoves;
	}

	private boolean hasNoLegalMoves() {
		return getAllLegalMoves().isEmpty();
	}

	private boolean isCheck() {
		boolean isWhite = getBoard().getFen().isWhite();
		IBitboard king = getBoard().getKing(isWhite);
		IBitboard attacks = getBoard().getAllOpponentAttacks();
		return Bitboard.overlap(king, attacks);
	}

	private boolean isPlaying() {
		return (
			getGameState() == State.Playing ||
			getGameState() == State.Promotion ||
			getGameState() == State.DrawOffer
		);
	}

	private void printGame() {
		System.out.println(getBoard());
		System.out.println(getBoard().getFen());
	}

	private void printCheckmate() {
		if (isCheckmate()) {
			setGameState(State.Checkmate);
			System.out.println("Checkmate!");
			System.out.println(getBoard().getFen().isWhite() ? "Black won." : "White won.");
		}
	}

	private void printStalemate() {
		if (isStalemate()) {
			setGameState(State.Stalemate);
			System.out.println("Stalemate!");
		}
	}

	private void printDraw() {
		if (getHalfMoveClock() >= ForsythEdwardsNotation.MAX_HALF_MOVE_CLOCK) {
			setGameState(State.Draw);
			System.out.println("Draw!");
		}
	}

	private void setGameState(State state) {
		getStateReader().flush();
		getStateWriter().write(state);
	}

	private IReader<State> getStateReader() {
		return stateReader;
	}

	private void setStateReader(BlockingQueue<State> stateQueue) {
		this.stateReader = new Reader<>(stateQueue);
	}

	private IWriter<State> getStateWriter() {
		return stateWriter;
	}

	private void setStateWriter(BlockingQueue<State> stateQueue) {
		this.stateWriter = new Writer<>(stateQueue);
	}

	private BlockingQueue<State> getStateQueue() {
		return stateQueue;
	}

	private void setStateQueue(BlockingQueue<State> stateQueue) {
		this.stateQueue = stateQueue;
	}

	private void setMoveList(IMoveList moveList) {
		this.moveList = moveList;
	}

	private void setBoard(IBoard board) {
		this.board = board;
	}

	private IReader<String> getReader() {
		return reader;
	}

	private void setReader(BlockingQueue<String> queue) {
		reader = new Reader<>(queue);
	}

	private IWriter<String> getWriter() {
		return writer;
	}

	private void setWriter(BlockingQueue<String> queue) {
		this.writer = new Writer<>(queue);
	}

	private BlockingQueue<String> getDataQueue() {
		return dataQueue;
	}

	private void setDataQueue(BlockingQueue<String> dataQueue) {
		this.dataQueue = dataQueue;
	}

	private BlockingQueue<Square[]> getMoveQueue() {
		return moveQueue;
	}

	private void setMoveQueue(BlockingQueue<Square[]> moveQueue) {
		this.moveQueue = moveQueue;
	}

	private IReader<Square[]> getMoveReader() {
		return moveReader;
	}

	private void setMoveReader(BlockingQueue<Square[]> moveQueue) {
		this.moveReader = new Reader<>(moveQueue);
	}

	private IWriter<Square[]> getMoveWriter() {
		return moveWriter;
	}

	private void setMoveWriter(BlockingQueue<Square[]> moveQueue) {
		this.moveWriter = new Writer<>(moveQueue);
	}

	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private void setMoveThread(Thread moveThread) {
		this.moveThread = moveThread;
	}

	private Thread getDrawOfferThread() {
		return drawOfferThread;
	}

	private void setDrawOfferThread(Thread drawOfferThread) {
		this.drawOfferThread = drawOfferThread;
	}

	private Semaphore getSemaphore() {
		return semaphore;
	}

	private void initMoveThread() {
		Thread moveThread = new Thread(() -> {
			release();
			if (getGameState() == State.Promotion) {
				setGameState(State.Playing);
			}
			while (isPlaying()) {
				Square[] move = getMoveReader().read();
				if (move == null) {
					continue;
				}
				try {
					getMoveList()
						.makeMove(move[0], move[1], getBoard(), getReader(), getStateWriter());
				} catch (Error e) {
					setErrorMessage(e.getMessage());
				} finally {
					release();
					getMoveReader().flush();
				}
				printGame();
				printCheckmate();
				printStalemate();
				printDraw();
			}
			System.out.println(getMoveList());
		});
		setMoveThread(moveThread);
	}

	private void initDrawOfferThread() {
		Thread drawOfferThread = new Thread(() -> {
			State oldState = getGameState();
			setGameState(State.DrawOffer);
			System.out.println("Draw offered! Accept ? (Y)");
			String answer = getReader().read();
			if (answer == null) {
				System.out.println("Draw declined");
				setGameState(oldState);
				return;
			}
			if (answer.equals("Y")) {
				System.out.println("Draw accepted");
				setGameState(State.Draw);
			}
		});
		setDrawOfferThread(drawOfferThread);
	}
}

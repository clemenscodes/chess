package model;

import api.model.IChessModel;
import api.model.Square;
import api.model.State;
import java.io.InputStream;
import java.util.ArrayList;

public class ChessModel implements IChessModel {

	private State state;
	private IBoard board;
	private IMoveList moveList;
	private IReader reader;

	public ChessModel(InputStream in) {
		setReader(in);
	}

	public ChessModel() {
		setReader(System.in);
	}

	public State getGameState() {
		return state;
	}

	public String getMoves() {
		return getMoveList().toString();
	}

	IBoard getBoard() {
		return board;
	}

	IMoveList getMoveList() {
		return moveList;
	}

	public void startGame() {
		setBoard(new Board());
		setMoveList(new MoveList());
		setGameState(State.Start);
		printGame();
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

	public void startGame(String fen) {
		setBoard(new Board(new ForsythEdwardsNotation(fen)));
		setMoveList(new MoveList());
		setGameState(State.Start);
		printGame();
	}

	public void startNewGame() {
		startGame();
		setGameState(State.Playing);
	}

	public void makeMove(Square source, Square destination) {
		if (getGameState() != State.Playing) {
			System.err.println("Can not make moves");
			return;
		}
		if (getHalfMoveClock() >= ForsythEdwardsNotation.MAX_HALF_MOVE_CLOCK) {
			setGameState(State.Draw);
			return;
		}
		getMoveList().makeMove(source, destination, getBoard(), getReader());
		printGame();
		if (isCheckmate()) {
			setGameState(State.Checkmate);
			System.out.println("Checkmate!");
			System.out.println(getBoard().getFen().isWhite() ? "Black won." : "White won.");
			System.out.println(getMoveList());
		}
		if (isStalemate()) {
			setGameState(State.Stalemate);
			System.out.println("Stalemate!");
			System.out.println(getMoveList());
		}
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
		setGameState(State.Resignation);
	}

	public void offerDraw() {
		System.out.println("Draw offered! Accept ? (Y)");
		String answer = getReader().readLine();
		if (answer.equals("Y")) {
			setGameState(State.Draw);
		}
	}

	public void claimDraw() {
		setGameState(State.Draw);
	}

	public ArrayList<Square[]> getLegalMoves(Square square) {
		IPiece piece = getBoard().getPiece(square);
		ArrayList<Square[]> allMoves = piece.getPieceMoves(
			getBoard(),
			Bitboard.getSingleBit(Board.getIndex(square))
		);
		return getLegalMoves(allMoves);
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

	public boolean isCheckmate() {
		return isCheck() && hasNoLegalMoves();
	}

	public boolean isStalemate() {
		return !isCheck() && hasNoLegalMoves();
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

	private void printGame() {
		System.out.println(getBoard());
		System.out.println(getBoard().getFen());
	}

	private IReader getReader() {
		return reader;
	}

	private void setReader(InputStream in) {
		reader = new Reader(in);
	}

	private void setGameState(State state) {
		this.state = state;
	}

	private void setMoveList(IMoveList moveList) {
		this.moveList = moveList;
	}

	private void setBoard(IBoard board) {
		this.board = board;
	}
}

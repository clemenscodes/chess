import bits.Bitboard;
import board.Board;
import java.io.InputStream;
import model.IChessModel;
import model.State;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.move.IMoveList;
import model.reader.IReader;
import move.MoveList;
import reader.Reader;

public class ChessModel implements IChessModel {

	private State state;
	private IBoard board;
	private IMoveList moveList;
	private IReader reader;

	public static void main(String[] args) {}

	public ChessModel(InputStream in) {
		setReader(in);
	}

	public ChessModel() {
		setReader(System.in);
	}

	public State getGameState() {
		return state;
	}

	public IBoard getBoard() {
		return board;
	}

	public IMoveList getMoveList() {
		return moveList;
	}

	public IReader getReader() {
		return reader;
	}

	public void startGame() {
		setBoard(new Board());
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
		getMoveList().makeMove(source, destination, getBoard(), getReader());
		printGame();
		boolean isWhite = getBoard().getFen().isWhite();
		if (isCheckmate()) {
			setGameState(State.Checkmate);
			System.out.println("Checkmate!");
			System.out.println(isWhite ? "Black won." : "White won.");
			System.out.println(getMoveList());
		}
		if (isStalemate()) {
			setGameState(State.Stalemate);
			System.out.println("Stalemate!");
			System.out.println(getMoveList());
		}
	}

	public void resign() {
		setGameState(State.GameOver);
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

	private void printGame() {
		System.out.println(getBoard());
		System.out.println(getBoard().getFen());
	}

	private void setReader(InputStream in) {
		reader = new Reader(in);
	}

	public boolean isCheckmate() {
		return isCheck() && hasNoLegalMoves();
	}

	public boolean isStalemate() {
		return !isCheck() && hasNoLegalMoves();
	}

	private boolean hasNoLegalMoves() {
		var moves = getBoard().getAllMoves(getBoard().getFen().isWhite());
		for (var move : moves) {
			try {
				return getBoard()
					.getPiece(move[0])
					.isInvalidMove(Board.getIndex(move[0]), Board.getIndex(move[1]), getBoard());
			} catch (Error ignored) {}
		}
		return true;
	}

	private boolean isCheck() {
		boolean isWhite = board.getFen().isWhite();
		IBitboard king = board.getKing(isWhite);
		IBitboard attacks = isWhite ? board.getAllOpponentAttacks() : board.getAllFriendlyAttacks();
		return Bitboard.overlap(king, attacks);
	}
}

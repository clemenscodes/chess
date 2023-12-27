package model;

import java.io.InputStream;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.fen.ForsythEdwardsNotation;
import model.fen.IForsythEdwardsNotation;
import model.move.IMove;
import model.move.IMoveList;
import model.move.Move;
import model.move.MoveList;
import model.util.io.reader.IReader;
import model.util.io.reader.Reader;

public class ChessModel implements IChessModel {

	private State state;
	private IForsythEdwardsNotation fen;
	private IBoard board;
	private IMoveList moveList;
	private IReader reader;

	public ChessModel(InputStream input) {
		setReader(input);
	}

	public ChessModel() {
		setReader(System.in);
	}

	public static void main(String[] args) {
		var model = new ChessModel();
		model.startGame();
		model.makeMove(new Move(Square.e2, Square.e4));
		model.makeMove(new Move(Square.d7, Square.d5));
	}

	public State getGameState() {
		return state;
	}

	public IForsythEdwardsNotation getFen() {
		return fen;
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
		setFen(new ForsythEdwardsNotation());
		setBoard(new Board());
		getBoard().setPieces(getFen().getPiecePlacementData());
		setMoveList(new MoveList());
		setGameState(State.Start);
		printGame();
	}

	public void startNewGame() {
		startGame();
		setGameState(State.Playing);
	}

	public void makeMove(IMove move) {
		getMoveList().makeMove(move, getBoard(), getReader());
		printGame();
	}

	public void unmakeMove() {
		getMoveList().unmakeMove(getBoard());
		printGame();
	}

	private void setGameState(State state) {
		this.state = state;
	}

	private void setFen(IForsythEdwardsNotation fen) {
		this.fen = fen;
	}

	private void setMoveList(IMoveList moveList) {
		this.moveList = moveList;
	}

	private void setBoard(IBoard board) {
		this.board = board;
	}

	private void printNext() {
		System.out.print("Next turn: ");
		System.out.println(fen.getActiveColor() == 'w' ? "White" : "Black");
	}

	private void printGame() {
		System.out.println(getBoard());
		printNext();
	}

	private void setReader(InputStream input) {
		this.reader = new Reader(input);
	}
}

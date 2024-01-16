package model;

import java.io.InputStream;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.move.IMoveList;
import model.move.MoveList;
import model.reader.IReader;
import model.reader.Reader;

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
		if (State.isCheckmate(board)) {
			setGameState(State.Checkmate);
			System.out.println("Checkmate!");
			System.out.println(isWhite ? "Black won." : "White won.");
			System.out.println(getMoveList());
		}
		if (State.isStalemate(board)) {
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
}

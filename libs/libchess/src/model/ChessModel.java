package model;

import static model.board.Square.*;

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

	public static void main(String[] args) {
		var model = new ChessModel();
		model.startGame();
		model.makeMove(e2, e4);
		model.makeMove(e7, e5);
		model.makeMove(g1, f3);
		model.makeMove(b8, c6);
		model.makeMove(f1, b5);
		model.makeMove(f8, c5);
		model.makeMove(b5, c6);
		model.makeMove(b7, c6);
		model.makeMove(f3, e5);
		model.makeMove(d8, f6);
		model.makeMove(e5, f3);
		model.makeMove(a8, b8);
		model.makeMove(e1, f1);
		System.out.println(model.getMoveList());
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
		getMoveList().makeMove(source, destination, getBoard(), getReader());
		printGame();
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

	private void printNext() {
		System.out.print("Next turn: ");
		System.out.println(getBoard().getFen().getActiveColor() == 'w' ? "White" : "Black");
	}

	private void printGame() {
		System.out.println(getBoard());
		printNext();
	}

	private void setReader(InputStream in) {
		reader = new Reader(in);
	}
}

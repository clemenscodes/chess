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
		model.makeMove(d2, d4);
		model.makeMove(d7, d5);
		model.makeMove(g1, f3);
		model.makeMove(c8, g4);
		model.makeMove(e2, e3);
		model.makeMove(e7, e5);
		model.makeMove(f1, e2);
		model.makeMove(b8, c6);
		model.makeMove(e1, h1);
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

	private void printGame() {
		System.out.println(getBoard());
		System.out.println(getBoard().getFen());
	}

	private void setReader(InputStream in) {
		reader = new Reader(in);
	}
}

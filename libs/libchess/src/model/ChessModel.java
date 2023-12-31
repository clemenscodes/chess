package model;

import static model.board.Square.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.fen.ForsythEdwardsNotation;
import model.fen.IForsythEdwardsNotation;
import model.move.IMoveList;
import model.move.MoveList;
import model.uci.IUniversalChessInterface;
import model.uci.UniversalChessInterface;
import model.util.io.reader.IReader;
import model.util.io.reader.Reader;

public class ChessModel implements IChessModel {

	private State state;
	private IForsythEdwardsNotation fen;
	private IBoard board;
	private IMoveList moveList;
	private IReader reader;
	private IUniversalChessInterface uci;

	public ChessModel(BlockingQueue<String> queue) {
		setReader(queue);
	}

	public ChessModel() {
		setReader(new LinkedBlockingQueue<>());
	}

	public static void main(String[] args) {
		var model = new ChessModel();
		model.startGame();
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
		setUci(new UniversalChessInterface(getReader()));
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

	public void makeMove(Square source, Square destination) {
		getMoveList().makeMove(source, destination, getBoard(), getReader());
		printGame();
	}

	public void initUci() {
		new Thread(getUci()).start();
	}

	private IUniversalChessInterface getUci() {
		return uci;
	}

	private void setUci(IUniversalChessInterface uci) {
		this.uci = uci;
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

	private void setReader(BlockingQueue<String> queue) {
		this.reader = new Reader(queue);
	}
}

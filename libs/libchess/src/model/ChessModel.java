package model;

import model.board.Board;
import model.board.IBoard;
import model.fen.ForsythEdwardsNotation;
import model.fen.IForsythEdwardsNotation;
import model.move.IMove;
import model.move.IMoveList;
import model.move.MoveList;

public class ChessModel implements IChessModel {

	private State state;
	private IForsythEdwardsNotation fen;
	private IBoard board;
	private IMoveList moveList;

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

	public void startGame() {
		setFen(new ForsythEdwardsNotation());
		setBoard(new Board());
		getBoard().initializePieces(getFen().getPiecePlacementData());
		setMoveList(new MoveList());
		setGameState(State.Start);
		printGame();
	}

	public void startNewGame() {
		startGame();
		setGameState(State.Playing);
	}

	public void makeMove(IMove move) {
		var board = getMoveList().makeMove(move, getBoard());
		setBoard(board);
		printGame();
	}

	public void unmakeMove() {
		var board = getMoveList().unmakeMove(getBoard());
		setBoard(board);
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
}

package model;

import model.enums.GameState;
import model.move.MoveList;

public class ChessModel implements IChessModel {

	private GameState state;
	private ForsythEdwardsNotation fen;
	private MoveList moveList;
	private Board board;

	public static void main(String[] args) {
		var model = new ChessModel();
		model.startGame();
	}

	public GameState getGameState() {
		return state;
	}

	public MoveList getMoveList() {
		return moveList;
	}

	public ForsythEdwardsNotation getFen() {
		return fen;
	}

	public Board getBoard() {
		return board;
	}

	public void startGame() {
		setFen(new ForsythEdwardsNotation());
		setBoard(new Board());
		getBoard().initializePieces(getFen().getPiecePlacementData());
		setMoveList(new MoveList());
		setGameState(GameState.Start);
		printGame();
	}

	public void startNewGame() {
		startGame();
		setGameState(GameState.Playing);
	}

	private void setGameState(GameState state) {
		this.state = state;
	}

	private void setFen(ForsythEdwardsNotation fen) {
		this.fen = fen;
	}

	private void setMoveList(MoveList moveList) {
		this.moveList = moveList;
	}

	private void setBoard(Board board) {
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

package model;

import model.enums.GameState;

public class ChessModel implements IChessModel {

	public static int FILES = 8;
	public static int RANKS = 8;
	private GameState state;
	private ForsythEdwardsNotation fen;

	public static void main(String[] args) {
		var model = new ChessModel();
		model.setFen(new ForsythEdwardsNotation());
		model.startGame();
	}

	public GameState getGameState() {
		return state;
	}

	public ForsythEdwardsNotation getFen() {
		return fen;
	}

	public void startGame() {
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

	private void printNext() {
		System.out.print("Next turn: ");
		System.out.println(fen.getActiveColor() == 'w' ? "White" : "Black");
	}

	private void printGame() {
		printNext();
	}
}

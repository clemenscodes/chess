package model;

import model.data.GameState;
import model.data.Player;

public class ChessModel {

	private GameState state;
	private Player white;
	private Player black;

	public void startGame(int width, int height) {
		setGameState(GameState.START);
	}

	public void startNewGame(int width, int height) {
		setGameState(GameState.PLAYING);
	}

	public void moveWhite(float distance) {
		return;
	}

	public void moveBlack(float distance) {
		return;
	}

	public GameState getGameState() {
		return state;
	}

	public Player getWhite() {
		return white;
	}

	public Player getBlack() {
		return black;
	}

	private void setGameState(GameState state) {
		this.state = state;
	}

	private void setWhite(Player white) {
		this.white = white;
	}

	private void setBlack(Player black) {
		this.black = black;
	}
}

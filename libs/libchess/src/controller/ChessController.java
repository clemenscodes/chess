package controller;

import data.GameState;
import data.player.Black;
import data.player.White;
import model.ChessModel;
import tcp.ConnectionManager;
import tcp.DataManager;
import view.IChessView;

public class ChessController implements IChessController {

	private ChessModel model;
	private ConnectionManager connectionManager;
	private DataManager dataManager;
	private IChessView view;
	protected int width;
	protected int height;

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public void setModel(ChessModel model) {
		this.model = model;
	}

	public void setView(IChessView view) {
		this.view = view;
	}

	public void startGame(int width, int height) {
		this.width = width;
		this.height = height;
		model.startGame(width, height);
	}

	public void startNewGame() {
		model.startNewGame(width, height);
	}

	public void nextFrame() {
		GameState state = this.model.getGameState();
		view.setBackground();
		switch (state) {
			case START -> view.drawStart();
			case PLAYING -> view.drawPlaying();
			case GAME_OVER -> view.drawGameOver();
			default -> throw new IllegalStateException(
				"Unexpected value: " + state
			);
		}
	}

	public void moveWhite(float distance) {
		model.moveWhite(distance);
	}

	public void moveBlack(float distance) {
		model.moveBlack(distance);
	}

	public GameState getGameState() {
		return model.getGameState();
	}

	public White getWhite() {
		return model.getWhite();
	}

	public Black getBlack() {
		return model.getBlack();
	}

	public void handleUserInput(char key, int keyCode) {
		GameState state = getGameState();
		switch (state) {
			case START, GAME_OVER -> handleStartOrGameOverInput(key);
			case PLAYING -> handlePlayingInput(key, keyCode);
			default -> throw new IllegalStateException(
				"Unexpected value: " + state
			);
		}
	}

	private void handleStartOrGameOverInput(char key) {
		if (key == ' ') {
			startNewGame();
		}
	}

	protected void handlePlayingInput(char key, int keyCode) {
		handleWhiteInput(key);
		handleBlackInput(keyCode);
	}

	protected void handleWhiteInput(char key) {}

	protected void handleBlackInput(int keyCode) {}
}

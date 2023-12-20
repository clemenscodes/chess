package controller;

import model.IChessModel;
import model.enums.GameState;
import tcp.IConnectionManager;
import tcp.IDataManager;
import view.IChessView;

public class ChessController implements IChessController {

	private IConnectionManager connectionManager;
	private IDataManager dataManager;
	private IChessModel model;
	private IChessView view;
	protected int width;
	protected int height;

	public void setDataManager(IDataManager dataManager) {
		this.dataManager = dataManager;
	}

	public void setConnectionManager(IConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public void setModel(IChessModel model) {
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
		var state = model.getGameState();
		model.printBoard();
		view.setBackground();
		switch (state) {
			case Start -> view.drawStart();
			case Playing -> view.drawPlaying();
			case GameOver -> view.drawGameOver();
			default -> throw new IllegalStateException(
				"Unexpected value: " + state
			);
		}
	}

	public GameState getGameState() {
		return model.getGameState();
	}

	public void handleUserInput(char key, int keyCode) {
		var state = getGameState();
		switch (state) {
			case Start, GameOver -> handleStartOrGameOverInput(key);
			case Playing -> handlePlayingInput(key, keyCode);
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

package controller;

import model.IChessModel;
import model.State;
import view.IChessView;

public class ChessController implements IChessController {

	private IChessModel model;
	private IChessView view;

	public void setModel(IChessModel model) {
		this.model = model;
	}

	public void setView(IChessView view) {
		this.view = view;
	}

	public void startGame() {
		getModel().startGame();
	}

	public void startNewGame() {
		getModel().startNewGame();
	}

	public void nextFrame() {
		State state = getModel().getGameState();
		getView().setBackground();
		switch (state) {
			case Start -> getView().drawStart();
			case Playing -> getView().drawPlaying();
			case Checkmate -> getView().drawCheckmate();
			case Stalemate -> getView().drawStalemate();
			case GameOver -> getView().drawGameOver();
			default -> throw new IllegalStateException("Unexpected value: " + state);
		}
	}

	public State getGameState() {
		return getModel().getGameState();
	}

	public void handleUserInput(char key, int keyCode) {
		var state = getGameState();
		switch (state) {
			case Start, GameOver -> handleStartOrGameOverInput(key);
			case Playing -> handlePlaying(key, keyCode);
			case Checkmate -> handleCheckmate(key, keyCode);
			case Stalemate -> handleStalemate(key, keyCode);
			default -> throw new IllegalStateException("Unexpected value: " + state);
		}
	}

	private IChessModel getModel() {
		return model;
	}

	private IChessView getView() {
		return view;
	}

	private void handleStartOrGameOverInput(char key) {
		System.out.println("handleStartGameOver");
	}

	private void handlePlaying(char key, int keyCode) {
		System.out.println("handlePlaying");
	}

	private void handleCheckmate(char key, int keyCode) {
		System.out.println("handleCheckmate");
	}

	private void handleStalemate(char key, int keyCode) {
		System.out.println("handleStalemate");
	}
}

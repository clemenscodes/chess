package controller;

import model.PokePongModel;
import model.data.Ball;
import model.data.GameState;
import model.data.Player;
import view.IPokePongView;

public class PokePongController implements IPokePongController {

	private PokePongModel model;
	private IPokePongView view;

	public PokePongController() {}

	public void setModel(PokePongModel model) {
		this.model = model;
	}

	public void setView(IPokePongView view) {
		this.view = view;
	}

	public void startGame(
		int width,
		int height,
		int PLAYER_SIZE,
		int PADDLE_WIDTH,
		int BALL_SIZE
	) {
		model.startGame(width, height, PLAYER_SIZE, PADDLE_WIDTH, BALL_SIZE);
	}

	public void startNewGame(
		int width,
		int height,
		int PLAYER_SIZE,
		int PADDLE_WIDTH,
		int BALL_SIZE
	) {
		model.startNewGame(width, height, PLAYER_SIZE, PADDLE_WIDTH, BALL_SIZE);
	}

	public void nextFrame() {
		view.drawGame();
	}

	public void updateBallPosition(float frameRate) {
		model.updateBallPosition(frameRate);
	}

	public void handleCollisions(int width, int height) {
		model.handleCollisions(width, height);
	}

	public void movePlayerOne(float distance) {
		model.movePlayerOne(distance);
	}

	public void movePlayerTwo(float distance) {
		model.movePlayerTwo(distance);
	}

	public GameState getGameState() {
		return model.getGameState();
	}

	public Player getPlayerOne() {
		return model.getPlayerOne();
	}

	public Player getPlayerTwo() {
		return model.getPlayerTwo();
	}

	public Ball getBall() {
		return model.getBall();
	}
}

package controller;

import model.PokePongModel;
import model.data.Ball;
import model.data.GameState;
import model.data.Player;
import view.IPokePongView;

public class PokePongController implements IPokePongController {

	protected PokePongModel model;
	protected IPokePongView view;
	protected int width;
	protected int height;
	protected int PLAYER_SIZE;
	protected int PADDLE_WIDTH;
	protected int BALL_SIZE;

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
		this.width = width;
		this.height = height;
		this.PLAYER_SIZE = PLAYER_SIZE;
		this.PADDLE_WIDTH = PADDLE_WIDTH;
		this.BALL_SIZE = BALL_SIZE;
		model.startGame(width, height, PLAYER_SIZE, PADDLE_WIDTH, BALL_SIZE);
	}

	public void startNewGame() {
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
		handlePlayerOneInput(key);
		handlePlayerTwoInput(keyCode);
	}

	protected void handlePlayerOneInput(char key) {
		var player = getPlayerOne();
		switch (key) {
			case 'w' -> movePlayerOne(
				Math.max((player.getSize() / 2.0f), player.position.y - 10)
			);
			case 's' -> movePlayerOne(
				Math.min(
					(height - player.getSize() / 2.0f),
					player.position.y + 10
				)
			);
		}
	}

	protected void handlePlayerTwoInput(int keyCode) {
		var playerTwo = getPlayerTwo();
		switch (keyCode) {
			case 38 -> movePlayerTwo(
				Math.max(
					(playerTwo.getSize() / 2.0f),
					playerTwo.position.y - 10
				)
			);
			case 40 -> movePlayerTwo(
				Math.min(
					(height - playerTwo.getSize() / 2.0f),
					playerTwo.position.y + 10
				)
			);
		}
	}
}

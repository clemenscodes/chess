package model;

import model.data.Ball;
import model.data.GameState;
import model.data.Player;
import model.data.PongData;

public class PokePongModel {

	private PongData data;

	public PokePongModel() {}

	public static void main(String[] args) {
		var game = new PokePongModel();
		game.startGame(900, 600, 50, 5, 25);
	}

	public void startGame(
		int width,
		int height,
		int PLAYER_SIZE,
		int PADDLE_WIDTH,
		int BALL_SIZE
	) {
		data =
			new PongData(width, height, PLAYER_SIZE, PADDLE_WIDTH, BALL_SIZE);
		data.state = GameState.START;
	}

	public PongData getData() {
		return data;
	}

	public void setData(PongData data) {
		this.data = data;
	}

	public GameState getGameState() {
		return data.state;
	}

	public void setGameState(GameState state) {
		this.data.state = state;
	}

	public Player getPlayerOne() {
		return data.player1;
	}

	public Player getPlayerTwo() {
		return data.player2;
	}

	public Ball getBall() {
		return data.ball;
	}

	public void startNewGame(
		int width,
		int height,
		int PLAYER_SIZE,
		int PADDLE_WIDTH,
		int BALL_SIZE
	) {
		data =
			new PongData(width, height, PLAYER_SIZE, PADDLE_WIDTH, BALL_SIZE);
		play();
	}

	public void updateBallPosition(float frameRate) {
		data.ball.updateBallPosition(1.0 / frameRate);
	}

	public void movePlayerOne(float distance) {
		data.player1.position.y = distance;
	}

	public void movePlayerTwo(float distance) {
		data.player2.position.y = distance;
	}

	public void play() {
		data.state = GameState.PLAYING;
	}

	public void handleCollisions(int width, int height) {
		// Reflect on y-axis on top and bottom wall
		if (data.ball.collidesY(0) || data.ball.collidesY(height)) {
			data.ball.acceleration.y *= -1;
		}

		// Reflect on x-axis when paddle hits the ball
		if (data.player1.hits(data.ball) || data.player2.hits(data.ball)) {
			data.ball.acceleration.x *= -1;
		}

		// Game over on hitting left or right wall
		if (data.ball.collidesX(0) || data.ball.collidesX(width)) {
			data.state = GameState.GAME_OVER;
		}
	}
}

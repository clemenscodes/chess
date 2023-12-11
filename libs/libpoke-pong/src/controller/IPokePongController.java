package controller;

import model.data.Ball;
import model.data.GameState;
import model.data.Player;

public interface IPokePongController {
	GameState getGameState();

	Player getPlayerOne();

	Player getPlayerTwo();

	Ball getBall();

	void nextFrame();

	void updateBallPosition(float frameRate);

	void handleCollisions(int width, int height);

	void movePlayerOne(float distance);

	void movePlayerTwo(float distance);

	void startGame(
		int width,
		int height,
		int PLAYER_SIZE,
		int PADDLE_WIDTH,
		int BALL_SIZE
	);

	void startNewGame(
		int width,
		int height,
		int PLAYER_SIZE,
		int PADDLE_WIDTH,
		int BALL_SIZE
	);
}

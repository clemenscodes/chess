package model.data;

public class PongData {

	public GameState state;
	public Player player1;
	public Player player2;
	public Ball ball;

	public PongData(
		int width,
		int height,
		int PADDLE_WIDTH,
		int PLAYER_SIZE,
		int BALL_SIZE
	) {
		player1 =
			new Player(
				PLAYER_SIZE + PADDLE_WIDTH,
				(height - PLAYER_SIZE) / 2.0 - PADDLE_WIDTH,
				PLAYER_SIZE
			);
		player2 =
			new Player(
				width - PLAYER_SIZE - PADDLE_WIDTH,
				(height - PLAYER_SIZE) / 2.0 + PADDLE_WIDTH,
				PLAYER_SIZE
			);
		ball = new Ball(width / 2.0, height / 2.0, BALL_SIZE);
		ball.randomizeAcceleration();
	}
}

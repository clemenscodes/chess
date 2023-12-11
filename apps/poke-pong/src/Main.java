import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;

public class Main extends PApplet {

	final int PADDLE_WIDTH = 5;
	final int BALL_SIZE = 25;
	final int PLAYER_SIZE = 50;

	private PongData data;
	private PImage startScreen;
	private PImage gameOverScreen;
	private PImage player1Image;
	private PImage player2Image;
	private PImage ballImage;

	public static void main(String[] args) {
		PApplet.main(Main.class);
	}

	@Override
	public void settings() {
		size(900, 600); // Use aspect ratio 3:2
		pixelDensity(1);
	}

	@Override
	public void setup() {
		data = new PongData();
		data.state = GameState.START;
		startScreen = loadImage("images/StartScreen.jpg");
		gameOverScreen = loadImage("images/GameOverScreen.jpg");
		player1Image = loadImage("images/025.png");
		player2Image = loadImage("images/133.png");
		ballImage = loadImage("images/100.png");
	}

	private void startNewGame() {
		data.player1 =
			new Player(
				PLAYER_SIZE + PADDLE_WIDTH,
				(height - PLAYER_SIZE) / 2.0 - PADDLE_WIDTH,
				PLAYER_SIZE
			);
		data.player2 =
			new Player(
				width - PLAYER_SIZE - PADDLE_WIDTH,
				(height - PLAYER_SIZE) / 2.0 + PADDLE_WIDTH,
				PLAYER_SIZE
			);
		data.ball = new Ball(width / 2.0, height / 2.0, BALL_SIZE);
		data.ball.randomizeAcceleration();
	}

	@Override
	public void draw() {
		background(0);
		switch (data.state) {
			case START -> {
				imageMode(CORNER);
				image(startScreen, 0, 0, width, height);
			}
			case PLAYING -> {
				background(255);
				imageMode(CENTER);
				// Draw player 1 and paddle
				var p1 = data.player1;
				image(
					player1Image,
					(float) (p1.position.x - p1.getSize() / 2.0 - PADDLE_WIDTH),
					p1.position.y,
					p1.getSize(),
					p1.getSize()
				);
				fill(0);
				rect(
					p1.position.x - PADDLE_WIDTH,
					(float) (p1.position.y - p1.getSize() / 2.0),
					PADDLE_WIDTH,
					p1.getSize()
				);
				// Draw player 2 and paddle
				var p2 = data.player2;
				image(
					player2Image,
					(float) (p2.position.x + p2.getSize() / 2.0 + PADDLE_WIDTH),
					p2.position.y,
					p2.getSize(),
					p2.getSize()
				);
				rect(
					p2.position.x,
					(float) (p2.position.y - p2.getSize() / 2.0),
					PADDLE_WIDTH,
					p2.getSize()
				);
				// Draw ball
				translate(data.ball.position.x, data.ball.position.y);
				rotate(data.ball.getRotationAngle());
				image(
					ballImage,
					0,
					0,
					data.ball.getSize(),
					data.ball.getSize()
				);

				updateBallPosition();
				handleCollisions();
			}
			case GAME_OVER -> {
				imageMode(CORNER);
				image(gameOverScreen, 0, 0, width, height);
			}
			default -> throw new IllegalStateException(
				"Unexpected value: " + data.state
			);
		}
	}

	private void updateBallPosition() {
		data.ball.updateBallPosition(1.0 / frameRate);
	}

	private void handleCollisions() {
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

	@Override
	public void keyPressed(KeyEvent event) {
		switch (data.state) {
			case START, GAME_OVER -> {
				if (event.getKey() == ' ') {
					data.state = GameState.PLAYING;
					startNewGame();
				}
			}
			case PLAYING -> {
				// Player 1: w = up and s = down
				var p1 = data.player1;
				if (event.getKey() == 'w') p1.position.y =
					max((float) (p1.getSize() / 2.0), p1.position.y - 10);
				if (event.getKey() == 's') p1.position.y =
					min(
						(float) (height - p1.getSize() / 2.0),
						p1.position.y + 10
					);

				// Player 2: Up-key = up and Down-key = down
				var p2 = data.player2;
				if (event.getKeyCode() == UP) p2.position.y =
					max((float) (p2.getSize() / 2.0), p2.position.y - 10);
				if (event.getKeyCode() == DOWN) p2.position.y =
					min(
						(float) (height - p2.getSize() / 2.0),
						p2.position.y + 10
					);
			}
			default -> throw new IllegalStateException(
				"Unexpected value: " + data.state
			);
		}
	}
}

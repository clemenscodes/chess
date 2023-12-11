package view;

import controller.IPokePongController;
import model.data.GameState;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;

public class PokePongView extends PApplet implements IPokePongView {

	protected final int PADDLE_WIDTH = 5;
	protected final int PLAYER_SIZE = 50;
	protected final int BALL_SIZE = 25;
	private IPokePongController controller;
	private PImage startScreen;
	private PImage gameOverScreen;
	private PImage player1Image;
	private PImage player2Image;
	private PImage ballImage;

	public PokePongView(int width, int height) {
		setSize(width, height);
		pixelDensity(1);
	}

	@Override
	public void setup() {
		controller.startGame(
			width,
			height,
			PADDLE_WIDTH,
			PLAYER_SIZE,
			BALL_SIZE
		);
		startScreen = loadImage("images/StartScreen.jpg");
		gameOverScreen = loadImage("images/GameOverScreen.jpg");
		player1Image = loadImage("images/025.png");
		player2Image = loadImage("images/133.png");
		ballImage = loadImage("images/100.png");
	}

	@Override
	public void draw() {
		controller.nextFrame();
	}

	public void setController(IPokePongController controller) {
		this.controller = controller;
	}

	public void drawGame() {
		GameState state = controller.getGameState();
		background(0);
		switch (state) {
			case START -> drawStart();
			case PLAYING -> {
				background(255);
				imageMode(CENTER);
				drawPlayerOne();
				drawPlayerTwo();
				drawBall();
			}
			case GAME_OVER -> drawGameOver();
			default -> throw new IllegalStateException(
				"Unexpected value: " + state
			);
		}
	}

	public void exitGame() {
		stop();
	}

	void drawStart() {
		imageMode(CORNER);
		image(startScreen, 0, 0, width, height);
	}

	void drawPlayerTwo() {
		var p2 = controller.getPlayerTwo();
		image(
			player2Image,
			(p2.position.x + p2.getSize() / 2.0f + PADDLE_WIDTH),
			p2.position.y,
			p2.getSize(),
			p2.getSize()
		);
		rect(
			p2.position.x,
			(p2.position.y - p2.getSize() / 2.0f),
			PADDLE_WIDTH,
			p2.getSize()
		);
	}

	void drawPlayerOne() {
		var p1 = controller.getPlayerOne();
		image(
			player1Image,
			(p1.position.x - p1.getSize() / 2.0f - PADDLE_WIDTH),
			p1.position.y,
			p1.getSize(),
			p1.getSize()
		);
		fill(0);
		rect(
			p1.position.x - PADDLE_WIDTH,
			(p1.position.y - p1.getSize() / 2.0f),
			PADDLE_WIDTH,
			p1.getSize()
		);
	}

	void drawGameOver() {
		imageMode(CORNER);
		image(gameOverScreen, 0, 0, width, height);
	}

	void drawBall() {
		var ball = controller.getBall();
		translate(ball.position.x, ball.position.y);
		rotate(ball.getRotationAngle());
		image(ballImage, 0, 0, ball.getSize(), ball.getSize());
		controller.updateBallPosition(frameRate);
		controller.handleCollisions(width, height);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		controller.handleUserInput(key, keyCode);
	}
}

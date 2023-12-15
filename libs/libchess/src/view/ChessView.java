package view;

import controller.IChessController;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class ChessView extends PApplet implements IChessView {

	private IChessController controller;

	public ChessView(int width, int height) {
		setSize(width, height);
		pixelDensity(1);
	}

	@Override
	public void draw() {
		controller.nextFrame();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		controller.handleUserInput(key, keyCode);
	}

	@Override
	public void setup() {
		controller.startGame(width, height);
	}

	public void setController(IChessController controller) {
		this.controller = controller;
	}

	public void drawGameOver() {}

	public void setBackground() {
		background(0);
	}

	public void drawStart() {}

	public void drawPlaying() {}
}

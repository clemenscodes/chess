package view;

import controller.IChessController;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.opengl.PGraphics2D;

public class ChessView extends PApplet implements IChessView {

	private IChessController controller;

	@Override
	public void settings() {
		fullScreen();
		pixelDensity(displayDensity());
	}

	@Override
	public void setup() {
		controller.startGame();
	}

	@Override
	public void draw() {
		controller.nextFrame();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		controller.handleUserInput(key, keyCode);
	}

	public void setController(IChessController controller) {
		this.controller = controller;
	}

	public void drawGameOver() {}

	public void setBackground() {
		background(255);
	}

	public void drawStart() {}

	public void drawPlaying() {}

	private void fillWhite() {
		fill(237, 214, 176);
	}

	private void fillBlack() {
		fill(181, 136, 99);
	}
}

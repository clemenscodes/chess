package view;

import api.controller.IChessController;
import api.view.IChessView;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class ChessView extends PApplet implements IChessView {

	private IChessController controller;
	private ControlP5 cp5;

	public ChessView() {
		setCp5(new ControlP5(this));
	}

	@Override
	public void settings() {
		fullScreen();
		pixelDensity(displayDensity());
	}

	@Override
	public void setup() {
		getController().startGame();
	}

	@Override
	public void draw() {
		getController().nextFrame();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		getController().handleUserInput(key, keyCode);
	}

	public void setController(IChessController controller) {
		this.controller = controller;
	}

	public void setBackground() {
		background(255);
	}

	public void drawStart() {}

	public void drawPlaying() {}

	public void drawCheckmate() {}

	public void drawStalemate() {}

	public void drawGameOver() {}

	private ControlP5 getCp5() {
		return cp5;
	}

	private void setCp5(ControlP5 cp5) {
		this.cp5 = cp5;
	}

	private IChessController getController() {
		return controller;
	}

	private void fillWhite() {
		fill(237, 214, 176);
	}

	private void fillBlack() {
		fill(181, 136, 99);
	}
}

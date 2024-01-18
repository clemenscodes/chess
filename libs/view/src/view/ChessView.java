package view;

import api.controller.IChessController;
import api.view.IChessView;
import controlP5.ControlP5;
import java.sql.SQLOutput;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class ChessView extends PApplet implements IChessView {

	private IChessController controller;

	public void setController(IChessController controller) {
		this.controller = controller;
	}

	private IChessController getController() {
		return controller;
	}

	private ControlP5 cp5;

	private void setCp5(ControlP5 cp5) {
		this.cp5 = cp5;
	}

	private ControlP5 getCp5() {
		return cp5;
	}

	private int getLeftBoardOffset() {
		return leftBoardOffset;
	}

	private void setLeftBoardOffset(int leftBoardOffset) {
		this.leftBoardOffset = leftBoardOffset;
	}

	private int leftBoardOffset;

	private int getTopBoardOffset() {
		return topBoardOffset;
	}

	private void setTopBoardOffset(int topBoardOffset) {
		this.topBoardOffset = topBoardOffset;
	}

	private int topBoardOffset;

	private int getSquareSize() {
		return squareSize;
	}

	private void setSquareSize(int squareSize) {
		this.squareSize = squareSize;
	}

	private int squareSize;

	@Override
	public void settings() {
		fullScreen();
		pixelDensity(displayDensity());
	}

	@Override
	public void setup() {
		setCp5(new ControlP5(this));
		setSquareSize(height / 10);
		setLeftBoardOffset((width / 2) - getSquareSize() * 4);
		setTopBoardOffset(height / 10);
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

	public void setBackground() {
		background(255);
	}

	public void drawStart() {
		String[] piecePlacementData = getController().getPiecePlacementData();
		drawBoard();
	}

	private void drawBoard() {
		for (int rank = 8; rank > 0; rank--) {
			for (int file = 1; file <= 8; file++) {
				drawSquare(rank, file);
			}
		}
	}

	private void drawSquare(int rank, int file) {
		int boardX = getLeftBoardOffset() + (file - 1) * getSquareSize();
		int boardY = height - getTopBoardOffset() - rank * getSquareSize();
		boolean bothEven = rank % 2 == 0 && file % 2 == 0;
		boolean bothOdd = rank % 2 != 0 && file % 2 != 0;
		boolean printBlack = bothEven || bothOdd;
		if (printBlack) {
			fillBlack();
		} else {
			fillWhite();
		}
		square(boardX, boardY, getSquareSize());
	}

	public void drawPlaying() {}

	public void drawCheckmate() {}

	public void drawStalemate() {}

	public void drawGameOver() {}

	private void fillWhite() {
		fill(237, 214, 176);
	}

	private void fillBlack() {
		fill(181, 136, 99);
	}
}

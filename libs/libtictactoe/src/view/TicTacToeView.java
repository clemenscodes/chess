package view;

import controller.ITicTacToeController;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class TicTacToeView extends PApplet implements ITicTacToeView {

	private ITicTacToeController controller;
	protected final int size;
	protected final boolean darkMode;
	private final int boardSize = 3;

	public TicTacToeView(int size, boolean darkMode) {
		this.size = size;
		this.darkMode = darkMode;
		setSize(size, size);
	}

	public void setController(ITicTacToeController controller) {
		this.controller = controller;
	}

	public void setup() {
		System.out.println("Setup complete");
	}

	public void draw() {
		controller.nextFrame();
	}

	public void mouseClicked() {
		controller.userInput(mouseX, mouseY);
	}

	public int getSize() {
		return this.size;
	}

	public int getFieldSize() {
		return width / this.boardSize;
	}

	public void drawGame() {
		setColorScheme();
		if (controller.hasError()) {
			this.drawError(controller.getErrorMessage());
			return;
		}
		char winner = controller.getWinner();
		if (winner == '*') {
			drawBoard();
			return;
		}
		this.drawGameOver(winner);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (keyCode == 82) {
			controller.restart();
		}
	}

	void setColorScheme() {
		textSize(32);
		textAlign(CENTER, CENTER);
		strokeWeight(5);
		setBackgroundColor();
		setFillColor();
		setStrokeColor();
	}

	void setFillColor() {
		if (this.darkMode) {
			fill(255);
		} else {
			fill(0);
		}
	}

	void setStrokeColor() {
		if (this.darkMode) {
			stroke(255);
		} else {
			stroke(0);
		}
	}

	void setBackgroundColor() {
		if (this.darkMode) {
			background(0);
		} else {
			background(255);
		}
	}

	void drawGrid() {
		int fieldSize = getFieldSize();
		for (int i = 1; i < this.boardSize; i++) {
			line(0, i * fieldSize, width, i * fieldSize);
			line(i * fieldSize, 0, i * fieldSize, height);
		}
	}

	void drawBoard() {
		drawGrid();
		char[] board = controller.getBoard();
		int fieldSize = getFieldSize();
		for (int i = 0; i < this.boardSize; i++) {
			for (int j = 0; j < this.boardSize; j++) {
				int xMiddle = (j * fieldSize) + (fieldSize / 2);
				int yMiddle = (i * fieldSize) + (fieldSize / 2);
				int fieldNumber = i * this.boardSize + j;
				drawField(xMiddle, yMiddle, board[fieldNumber]);
			}
		}
	}

	void drawField(int x, int y, char value) {
		if (value == 'X') {
			text("X", x, y);
		} else if (value == 'O') {
			text("O", x, y);
		}
	}

	void drawError(String errorMessage) {
		float centerX = width / 2.0f;
		float centerY = height / 2.0f;
		fill(255, 0, 0);
		text(errorMessage, centerX, centerY);
		setFillColor();
		textSize(24);
		text("Click anywhere to resume the game", centerX, centerY + 200);
	}

	void drawGameOver(char winner) {
		float centerX = width / 2.0f;
		float centerY = height / 2.0f;
		text("GAME OVER", centerX, centerY);
		if (winner == '/') {
			text("DRAW", centerX, centerY + 100);
		}
		if (winner == 'X') {
			text("Player 1 (X) has won", centerX, centerY + 100);
		}
		if (winner == 'O') {
			text("Player 2 (O) has won", centerX, centerY + 100);
		}
		text("Click anywhere to restart the game :)", centerX, centerY + 200);
	}
}

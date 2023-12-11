package lib.view;

import lib.controller.ITicTacToeController;
import processing.core.PApplet;
import processing.event.KeyEvent;

/**
 * The TicTacToeView class represents the graphical user interface for the Tic Tac Toe game.
 * It extends PApplet and implements the ITicTacToeView interface to provide rendering
 * and interaction functionalities.
 */public class TicTacToeView extends PApplet implements ITicTacToeView {

	/**
	 * The controller responsible for handling game logic and user input.
	 */
	private ITicTacToeController controller;

	/**
	 * The size of the view (width and height).
	 */
	protected final int size;

	/**
	 * A boolean indicating whether to use dark mode or not.
	 */
	protected final boolean darkMode;

	/**
	 * The size of the game board, representing the number of rows and columns.
	 */
	private final int boardSize = 3;

	/**
	 * Constructs a TicTacToeView with the specified size and dark mode preference.
	 *
	 * @param size     The size of the view (width and height).
	 * @param darkMode A boolean indicating whether to use dark mode or not.
	 */
	public TicTacToeView(int size, boolean darkMode) {
		this.size = size;
		this.darkMode = darkMode;
		setSize(size, size);
	}

	/**
	 * Sets the controller for the TicTacToeView.
	 *
	 * @param controller The controller to set.
	 */
	public void setController(ITicTacToeController controller) {
		this.controller = controller;
	}

	/**
	 * Called once at the beginning of the program to perform setup tasks.
	 */
	public void setup() {
		System.out.println("Setup complete");
	}

	/**
	 * Called continuously to draw the graphical elements on the screen.
	 */
	public void draw() {
		controller.nextFrame();
	}

	/**
	 * Called when the mouse is clicked to handle user input.
	 */
	public void mouseClicked() {
		controller.userInput(mouseX, mouseY);
	}

	/**
	 * Gets the size of the TicTacToeView.
	 *
	 * @return The size of the view.
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Gets the size of each field on the game board.
	 *
	 * @return The size of each field.
	 */
	public int getFieldSize() {
		return width / this.boardSize;
	}

	/**
	 * Draws the Tic Tac Toe game on the screen.
	 */
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

	/**
	 * Handles key presses, specifically the 'R' key to restart the game.
	 *
	 * @param event The KeyEvent representing the key press event.
	 */
	@Override
	public void keyPressed(KeyEvent event) {
		if (keyCode == 82) {
			controller.restart();
		}
	}

	/**
	 * Sets the color scheme for text, background, fill, and stroke based on the dark mode setting.
	 */
	void setColorScheme() {
		textSize(32);
		textAlign(CENTER, CENTER);
		strokeWeight(5);
		setBackgroundColor();
		setFillColor();
		setStrokeColor();
	}

	/**
	 * Sets the fill color based on the dark mode setting.
	 */
	void setFillColor() {
		if (this.darkMode) {
			fill(255);
		} else {
			fill(0);
		}
	}

	/**
	 * Sets the stroke color based on the dark mode setting.
	 */
	void setStrokeColor() {
		if (this.darkMode) {
			stroke(255);
		} else {
			stroke(0);
		}
	}

	/**
	 * Sets the background color based on the dark mode setting.
	 */
	void setBackgroundColor() {
		if (this.darkMode) {
			background(0);
		} else {
			background(255);
		}
	}

	/**
	 * Draws the grid lines on the game board.
	 */
	void drawGrid() {
		int fieldSize = getFieldSize();
		for (int i = 1; i < this.boardSize; i++) {
			line(0, i * fieldSize, width, i * fieldSize);
			line(i * fieldSize, 0, i * fieldSize, height);
		}
	}

	/**
	 * Draws the game board with X, O, or empty fields.
	 */
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

	/**
	 * Draws an individual game board field with the specified value (X, O, or empty).
	 *
	 * @param x     The x-coordinate of the field.
	 * @param y     The y-coordinate of the field.
	 * @param value The value to be drawn in the field.
	 */
	void drawField(int x, int y, char value) {
		if (value == 'X') {
			text("X", x, y);
		} else if (value == 'O') {
			text("O", x, y);
		}
	}

	/**
	 * Draws an error message on the screen, indicating an issue with the game.
	 *
	 * @param errorMessage The error message to be displayed.
	 */
	void drawError(String errorMessage) {
		float centerX = width / 2.0f;
		float centerY = height / 2.0f;
		fill(255, 0, 0);
		text(errorMessage, centerX, centerY);
		setFillColor();
		textSize(24);
		text("Click anywhere to resume the game", centerX, centerY + 200);
	}

	/**
	 * Draws the game-over message indicating the winner or a draw.
	 *
	 * @param winner The character representing the winner ('X', 'O') or a draw ('/').
	 */
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

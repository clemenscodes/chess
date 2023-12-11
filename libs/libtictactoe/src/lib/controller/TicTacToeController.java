package lib.controller;

import lib.model.TicTacToeModel;
import lib.view.ITicTacToeView;

/**
 * The TicTacToeController class serves as the controller in the Model-View-Controller (MVC)
 * architecture for the Tic Tac Toe game. It manages the interaction between the model
 * ({@link lib.model.TicTacToeModel}) and the view ({@link lib.view.ITicTacToeView}), handling
 * game logic, user input, and communication between the model and view components.
 */
public class TicTacToeController implements ITicTacToeController {

	/**
	 * The underlying model representing the game state.
	 */
	private TicTacToeModel model;

	/**
	 * The view responsible for rendering the game on the screen.
	 */
	private ITicTacToeView view;

	/**
	 * The character representing the winner of the game.
	 */
	private char winner = '*';

	/**
	 * A flag indicating whether an error condition is present.
	 */
	private boolean errorFlag = false;

	/**
	 * The error message associated with the error condition, if present.
	 */
	private String errorMessage;

	/**
	 * Constructs a TicTacToeController.
	 */
	public TicTacToeController() {}

	/**
	 * Sets the model for the controller.
	 *
	 * @param model The TicTacToeModel to set.
	 */
	public void setModel(TicTacToeModel model) {
		this.model = model;
	}

	/**
	 * Sets the view for the controller.
	 *
	 * @param view The ITicTacToeView to set.
	 */
	public void setView(ITicTacToeView view) {
		this.view = view;
	}

	/**
	 * Calculates and sets the winner of the game based on the current state of the model.
	 */
	public void calculateWinner() {
		if (model.hasPlayer1Won()) {
			this.winner = model.PLAYER_1;
			System.out.println("Player 1 won");
			return;
		}
		if (model.hasPlayer2Won()) {
			this.winner = model.PLAYER_2;
			System.out.println("Player 2 won");
			return;
		}
		this.winner = '/';
	}

	/**
	 * Checks if an error condition is present.
	 *
	 * @return True if an error condition is present, false otherwise.
	 */
	public boolean hasError() {
		return this.errorFlag;
	}

	/**
	 * Gets the error message associated with the error condition.
	 *
	 * @return The error message, or null if no error is present.
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * Resets the error condition.
	 */
	public void resetError() {
		this.errorFlag = false;
		this.errorMessage = null;
	}

	/**
	 * Gets the winner of the game.
	 *
	 * @return The character representing the winner ('X', 'O') or a draw ('/').
	 */
	public char getWinner() {
		return this.winner;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void nextFrame() {
		view.drawGame();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void userInput(int x, int y) {
		if (this.errorFlag) {
			this.resetError();
			return;
		}
		if (model.isGameOver()) {
			restart();
			return;
		}
		int size = this.view.getSize();
		int field = model.getFieldFromCoordinates(x, y, size);
		try {
			model.move(field);
		} catch (Exception e) {
			this.errorFlag = true;
			this.errorMessage = e.getMessage();
		}
		if (model.isGameOver()) {
			calculateWinner();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public char[] getBoard() {
		return model.getBoard();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void restart() {
		System.out.println("Restarting game...");
		this.winner = '*';
		model.newGame();
	}
}

package lib.controller;

/**
 * The ITicTacToeController interface defines the contract for a controller component
 * in the Model-View-Controller (MVC) architecture for the Tic Tac Toe game. Implementing
 * classes are expected to provide methods for managing game state, user input, and
 * communication with the view component.
 */
public interface ITicTacToeController {
	/**
	 * Advances the game to the next frame. This method is typically called repeatedly
	 * to update the game state and render it on the screen.
	 */
	void nextFrame();

	/**
	 * Handles user input at the specified coordinates (x, y). The implementation should
	 * interpret user input and update the game state accordingly.
	 *
	 * @param x The x-coordinate of the user input.
	 * @param y The y-coordinate of the user input.
	 */
	void userInput(int x, int y);

	/**
	 * Gets the current state of the game board. The returned array represents the
	 * content of each field on the board.
	 *
	 * @return The array representing the current state of the game board.
	 */
	char[] getBoard();

	/**
	 * Checks if an error condition is present in the game.
	 *
	 * @return True if an error condition is present, false otherwise.
	 */
	boolean hasError();

	/**
	 * Gets the error message associated with the error condition, if present.
	 *
	 * @return The error message, or null if no error is present.
	 */
	String getErrorMessage();

	/**
	 * Gets the character representing the winner of the game. Returns '*' if the game
	 * is ongoing, '/' for a draw, 'X' if player 1 has won, and 'O' if player 2 has won.
	 *
	 * @return The character representing the winner or the state of the game.
	 */
	char getWinner();

	/**
	 * Restarts the game, resetting the game state and allowing players to start a new match.
	 */
	void restart();
}

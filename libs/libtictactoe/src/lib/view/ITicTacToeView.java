package lib.view;

/**
 * The ITicTacToeView interface defines the contract for a view component in the Tic Tac Toe game.
 * Implementing classes are expected to provide methods for retrieving the size of the view and
 * rendering the game on the screen.
 */
public interface ITicTacToeView {
	/**
	 * Gets the size of the Tic Tac Toe view.
	 *
	 * @return The size of the view, representing both width and height.
	 */
	int getSize();

	/**
	 * Draws the Tic Tac Toe game on the screen.
	 * Implementing classes should provide the graphical rendering logic for the game.
	 */
	void drawGame();
}

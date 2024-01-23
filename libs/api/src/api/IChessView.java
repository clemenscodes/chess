package api;

/**
 * The {@link IChessView} interface defines methods for interacting with and updating the chess game view.
 * It is intended to be implemented by classes responsible for rendering the graphical user interface (GUI)
 * for a Chess MVC game, facilitating communication with the controller and providing visual feedback to the user.
 */
public interface IChessView {
	/**
	 * Sets the controller associated with the view.
	 *
	 * @param controller The controller implementing the {@link IChessController} interface.
	 */
	void setController(IChessController controller);

	/**
	 * Gets the left offset of the chessboard on the view.
	 *
	 * @return The left offset value.
	 */
	int getLeftBoardOffset();

	/**
	 * Gets the top offset of the chessboard on the view.
	 *
	 * @return The top offset value.
	 */
	int getTopBoardOffset();

	/**
	 * Gets the size of each square on the chessboard.
	 *
	 * @return The square size value.
	 */
	int getSquareSize();

	/**
	 * Gets the total width of the chess view.
	 *
	 * @return The width of the view.
	 */
	int getWidth();

	/**
	 * Gets the total height of the chess view.
	 *
	 * @return The height of the view.
	 */
	int getHeight();

	/**
	 * Draws the view in the starting state, typically showing the initial chessboard.
	 */
	void drawStart();

	/**
	 * Draws the view in the playing state, indicating an ongoing chess game.
	 */
	void drawPlaying();

	/**
	 * Draws the view in the checkmate state, indicating the end of the game with a decisive result.
	 */
	void drawCheckmate();

	/**
	 * Draws the view in the stalemate state, indicating a draw due to a lack of legal moves for the current player.
	 */
	void drawStalemate();

	/**
	 * Draws the view in the resignation state, indicating that a player has voluntarily resigned from the game.
	 */
	void drawResignation();

	/**
	 * Draws the view in the draw state, indicating the end of the game with a draw result.
	 */
	void drawDraw();

	/**
	 * Draws the view in the promotion state, indicating that a pawn is eligible for promotion.
	 */
	void drawPromotion();

	/**
	 * Draws the view in the draw offer state, indicating that a draw has been offered by one player to the opponent.
	 */
	void drawDrawOffer();
}

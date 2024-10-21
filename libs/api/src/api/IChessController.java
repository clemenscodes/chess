package api;

import java.util.ArrayList;

/**
 * The {@link IChessController} interface combines the functionalities of
 * managing chess game logic and
 * handling user interactions for a Chess MVC (Model-View-Controller) game.
 * It extends both {@link IChess} for game state and {@link IGame} for game
 * actions, providing a unified
 * interface for the controller component.
 */
public interface IChessController extends IChess, IGame {
    /**
     * Sets the model for the controller to interact with.
     *
     * @param model The model implementing the {@link IChessModel} interface.
     */
    void setModel(IChessModel model);

    /**
     * Sets the view for the controller to interact with.
     *
     * @param view The view implementing the {@link IChessView} interface.
     */
    void setView(IChessView view);

    /**
     * Advances the game to the next frame or state, updating the view accordingly.
     */
    void nextFrame();

    /**
     * Handles the event when the mouse is pressed on the chessboard.
     *
     * @param x The x-coordinate of the mouse press.
     * @param y The y-coordinate of the mouse press.
     */
    void handleMousePressed(int x, int y);

    /**
     * Handles the event when the mouse is dragged on the chessboard.
     *
     * @param x The x-coordinate of the mouse drag.
     * @param y The y-coordinate of the mouse drag.
     */
    void handleMouseDragged(int x, int y);

    /**
     * Handles the event when the mouse is released on the chessboard.
     *
     * @param x The x-coordinate of the mouse release.
     * @param y The y-coordinate of the mouse release.
     */
    void handleMouseReleased(int x, int y);

    /**
     * Handles the event when the mouse is moved over the chessboard.
     *
     * @param x The x-coordinate of the mouse move.
     * @param y The y-coordinate of the mouse move.
     */
    void handleMouseMoved(int x, int y);

    /**
     * Gets the error message associated with the last action or game state.
     *
     * @return A string representing an error message, or an empty string if there
     *         is no error.
     */
    String getErrorMessage();

    /**
     * Clears the error message, resetting it to an empty state.
     */
    void clearErrorMessage();

    /**
     * Gets the current Forsyth-Edwards Notation (FEN) representation of the chess
     * game.
     *
     * @return The FEN string representing the current game state.
     */
    String getFen();

    /**
     * Gets the source square of the last chess move.
     *
     * @return The source square.
     */
    Square getSource();

    /**
     * Gets the destination square of the last chess move.
     *
     * @return The destination square.
     */
    Square getDestination();

    /**
     * Gets the square being dragged to by the user.
     *
     * @return The square being dragged.
     */
    Square getDraggedSquare();

    /**
     * Gets the list of legal moves for the current player.
     *
     * @return An ArrayList containing pairs of squares representing legal moves.
     */
    ArrayList<Square[]> getLegalMoves();
}

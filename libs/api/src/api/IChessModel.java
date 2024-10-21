package api;

import java.util.ArrayList;

/**
 * The {@link IChessModel} interface combines the functionalities of managing
 * chess game state and providing
 * game-related information for a Chess MVC (Model-View-Controller) game. It
 * extends both {@link IChess}
 * for general game state and {@link IGame} for game actions, providing a
 * unified interface for the model component.
 */
public interface IChessModel extends IChess, IGame {
    /**
     * Gets the current Forsyth-Edwards Notation (FEN) representation of the chess
     * game.
     *
     * @return The FEN string representing the current game state.
     */
    String getFen();

    /**
     * Gets the list of legal moves for a specific square on the chessboard.
     *
     * @param square The square for which legal moves are to be determined.
     * @return An ArrayList containing pairs of squares representing legal moves for
     *         the specified square.
     */
    ArrayList<Square[]> getLegalMoves(Square square);

    /**
     * Clears any error messages or flags associated with the model.
     */
    void clearError();
}

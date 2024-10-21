package api;

/**
 * The {@link IGame} interface defines actions performed by players during a
 * chess game.
 * It is intended to be implemented by classes responsible for managing the game
 * state and player interactions,
 * typically implemented by the controller and model components of a Chess MVC
 * game.
 */
public interface IGame {
    /**
     * Initializes the chess board, move list and sets the game state to
     * {@link State#Start}
     */
    void startGame();

    /**
     * Initializes the chess board, move list and sets the game state to
     * {@link State#Playing}
     */
    void startNewGame();

    /**
     * Resigns from the current game, indicating the player's voluntary withdrawal.
     */
    void resign();

    /**
     * Offers a draw to the opponent.
     */
    void offerDraw();

    /**
     * Accepts a draw offer from the opponent.
     */
    void acceptDraw();

    /**
     * Declines a draw offer from the opponent.
     */
    void declineDraw();

    /**
     * Claims a draw based on a specific condition, such as a repeated position or
     * the 50-move rule.
     */
    void claimDraw();

    /**
     * Gets the current state of the chess game.
     *
     * @return The current state of the game (e.g., Start, Playing, Checkmate,
     *         Stalemate, etc.).
     */
    State getGameState();

    /**
     * Gets the sequence of moves made in the game.
     *
     * @return A string representing the sequence of moves in standard algebraic
     *         notation (SAN).
     */
    String getMoves();

    /**
     * Makes a move on the chessboard from the source square to the destination
     * square.
     *
     * @param source      The source square of the move.
     * @param destination The destination square of the move.
     */
    void makeMove(Square source, Square destination);

    /**
     * Promotes a pawn to a queen.
     */
    void promoteQueen();

    /**
     * Promotes a pawn to a rook.
     */
    void promoteRook();

    /**
     * Promotes a pawn to a knight.
     */
    void promoteKnight();

    /**
     * Promotes a pawn to a bishop.
     */
    void promoteBishop();

    /**
     * Checks if the player has their own piece on a specific square.
     *
     * @param square The square to check.
     * @return {@code true} if the player has their own piece on the square,
     *         {@code false} otherwise.
     */
    boolean isOwnPieceOnSquare(Square square);

    /**
     * Gets an error message associated with the last action or game state.
     *
     * @return A string representing an error message, or null if there is no error.
     */
    String getErrorMessage();
}

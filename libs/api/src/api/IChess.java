package api;

/**
 * The {@link IChess} interface defines functions related to the board state in a Forsyth-Edwards Notation (FEN) string.
 * It is intended to be implemented by classes that manage and represent the state of a chess game.
 */
public interface IChess {
	/**
	 * Gets the piece placement data in FEN format.
	 *
	 * @return An array representing the piece placement on the chessboard in FEN notation.
	 */
	String[] getPiecePlacementData();

	/**
	 * Checks if it is currently the turn of the white player.
	 *
	 * @return {@code true} if it is the white player's turn, {@code false} otherwise.
	 */
	boolean isWhite();

	/**
	 * Gets the castling availability in FEN format.
	 *
	 * @return A string representing the castling availability.
	 */
	String getCastling();

	/**
	 * Checks if white king-side castling is still available.
	 *
	 * @return {@code true} if white king-side castling is available, {@code false} otherwise.
	 */
	boolean getWhiteKingCastle();

	/**
	 * Checks if white queen-side castling is still available.
	 *
	 * @return {@code true} if white queen-side castling is available, {@code false} otherwise.
	 */
	boolean getWhiteQueenCastle();

	/**
	 * Checks if black king-side castling is still available.
	 *
	 * @return {@code true} if black king-side castling is available, {@code false} otherwise.
	 */
	boolean getBlackKingCastle();

	/**
	 * Checks if black queen-side castling is still available.
	 *
	 * @return {@code true} if black queen-side castling is available, {@code false} otherwise.
	 */
	boolean getBlackQueenCastle();

	/**
	 * Gets the en passant target square in FEN format.
	 *
	 * @return A string representing the en passant target square.
	 */
	String getEnPassant();

	/**
	 * Gets the half-move clock, representing the number of half-moves since the last pawn move or capture.
	 *
	 * @return The half-move clock value.
	 */
	int getHalfMoveClock();

	/**
	 * Gets the full move number, representing the number of the current full move.
	 * Increases every time black moves.
	 *
	 * @return The full move number.
	 */
	int getFullMoveNumber();
}

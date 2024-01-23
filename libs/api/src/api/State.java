package api;

/**
 * The {@link State} enum represents the various states of a chess game.
 * Each enum constant defines a specific state that the game can be in.
 */
public enum State {
	/**
	 * Represents the starting state of a chess game.
	 */
	Start,

	/**
	 * Represents the state when the game is actively being played.
	 */
	Playing,

	/**
	 * Represents the state when the game has reached a checkmate, indicating the end of the game with a decisive result.
	 */
	Checkmate,

	/**
	 * Represents the state when the game has reached a stalemate, resulting in a draw due to a lack of legal moves for the current player.
	 */
	Stalemate,

	/**
	 * Represents the state when a player has resigned, indicating a voluntary withdrawal from the game.
	 */
	Resignation,

	/**
	 * Represents the state when the game has ended in a draw due to a specific condition.
	 */
	Draw,

	/**
	 * Represents the state when a draw offer is made by one player to the opponent.
	 */
	DrawOffer,

	/**
	 * Represents the state when a pawn is promoted to another piece, typically a queen, upon reaching the opposite end of the board.
	 */
	Promotion,
}

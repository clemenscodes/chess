package lib.model;

/**
 * The TicTacToeModel class represents the game logic and state for the Tic Tac Toe game.
 * It manages the game board, player moves, win conditions, and provides methods for
 * interacting with the game state.
 */
public class TicTacToeModel {

	/**
	 * The default field entry for empty fields.
	 */
	public final char EMPTY = '_';

	/**
	 * The mark of player 1 on the playing field.
	 */
	public final char PLAYER_1 = 'X';

	/**
	 * The mark of player 2 on the playing field.
	 */
	public final char PLAYER_2 = 'O';

	/**
	 * The playing field with 9 entries.
	 */
	private char[] gameState;

	/**
	 * Counts the moves. Even moves are for PLAYER_1; odd moves are for PLAYER_2.
	 */
	private char moveCount = 0;

	/**
	 * Gets the current state of the game board.
	 *
	 * @return The array representing the current state of the game board.
	 */
	public char[] getBoard() {
		return this.gameState;
	}

	/**
	 * Constructs a new TicTacToeModel and initializes a new game.
	 */
	public TicTacToeModel() {
		newGame();
	}

	/**
	 * Initializes a new game by resetting the game state.
	 */
	public void newGame() {
		gameState =
			new char[] {
				EMPTY,
				EMPTY,
				EMPTY,
				EMPTY,
				EMPTY,
				EMPTY,
				EMPTY,
				EMPTY,
				EMPTY,
			};
		moveCount = 0;
	}

	/**
	 * Gets the entry at the specified field on the game board.
	 *
	 * @param field The index of the field to retrieve.
	 * @return The character representing the entry at the specified field.
	 * @throws IndexOutOfBoundsException if the field index is not in the range [0, 8].
	 */
	public char getFieldEntry(int field) {
		if (isValidFieldIndex(field)) {
			return gameState[field];
		}
		throw new IndexOutOfBoundsException("Valid fields are [0, 8]");
	}

	private boolean isValidFieldIndex(int field) {
		return field >= 0 && field < gameState.length;
	}

	/**
	 * Checks if a field on the game board is empty.
	 *
	 * @param field The index of the field to check.
	 * @return True if the field is empty, false otherwise.
	 * @throws IndexOutOfBoundsException if the field index is not in the range [0, 8].
	 */
	public boolean isEmptyField(int field) {
		if (isValidFieldIndex(field)) {
			return gameState[field] == EMPTY;
		}
		throw new IndexOutOfBoundsException("Valid fields are [0, 8]");
	}

	/**
	 * Gets the active player ('X' for player 1, 'O' for player 2) based on the move count.
	 *
	 * @return The character representing the active player.
	 */
	public char getActivePlayer() {
		return (moveCount % 2 == 0) ? PLAYER_1 : PLAYER_2;
	}

	/**
	 * Makes a move on the game board at the specified field.
	 *
	 * @param field The index of the field to make a move.
	 * @throws RuntimeException if the game is already over, the field index is invalid,
	 *                          or the chosen field is already taken.
	 */
	public void move(int field) {
		if (isGameOver()) {
			throw new RuntimeException(
				"The game is already over. Please start a new game."
			);
		}
		if (!isValidFieldIndex(field)) {
			throw new IndexOutOfBoundsException("Valid fields are [0, 8]");
		}
		if (!isEmptyField(field)) {
			throw new RuntimeException("The chosen field is already taken");
		}
		gameState[field] = getActivePlayer();
		moveCount++;
		// Print the state after the move. For easier play inside the JShell.
		System.out.println(this);
	}

	/**
	 * Checks if the game is over, either due to a win or a full board.
	 *
	 * @return True if the game is over, false otherwise.
	 */
	public boolean isGameOver() {
		return (
			hasPlayer1Won() || hasPlayer2Won() || moveCount >= gameState.length
		);
	}

	/**
	 * Checks if player 1 has won the game.
	 *
	 * @return True if player 1 has won, false otherwise.
	 */
	public boolean hasPlayer1Won() {
		return checkWinCondition(PLAYER_1);
	}

	/**
	 * Checks if player 2 has won the game.
	 *
	 * @return True if player 2 has won, false otherwise.
	 */
	public boolean hasPlayer2Won() {
		return checkWinCondition(PLAYER_2);
	}

	/**
	 * Checks if the specified player has met the win condition in any row, column, or diagonal.
	 *
	 * @param player The player to check for the win condition ('X' or 'O').
	 * @return True if the player has won, false otherwise.
	 */
	private boolean checkWinCondition(char player) {
		return ( // Check rows
			(gameState[0] == player &&
				gameState[1] == player &&
				gameState[2] == player) ||
			(gameState[3] == player &&
				gameState[4] == player &&
				gameState[5] == player) ||
			(gameState[6] == player &&
				gameState[7] == player &&
				gameState[8] == player) ||
			// Check columns
			(gameState[0] == player &&
				gameState[3] == player &&
				gameState[6] == player) ||
			(gameState[1] == player &&
				gameState[4] == player &&
				gameState[7] == player) ||
			(gameState[2] == player &&
				gameState[5] == player &&
				gameState[8] == player) ||
			// Check diagonals
			(gameState[0] == player &&
				gameState[4] == player &&
				gameState[8] == player) ||
			(gameState[2] == player &&
				gameState[4] == player &&
				gameState[6] == player)
		);
	}

	/**
	 * Calculates the field index on the game board based on the provided coordinates.
	 *
	 * @param x    The x-coordinate of the user input.
	 * @param y    The y-coordinate of the user input.
	 * @param size The size of the game board.
	 * @return The calculated field index.
	 */
	public int getFieldFromCoordinates(int x, int y, int size) {
		int rows = 3;
		int cols = 3;
		int fieldWidth = size / cols;
		int fieldHeight = size / rows;
		int colClicked = x / fieldWidth;
		int rowClicked = y / fieldHeight;
		int fieldNumber = rowClicked * cols + colClicked;
		System.out.printf(
			"Calculated field %d from coordinates [x: %d, y: %d]%n",
			fieldNumber,
			x,
			y
		);
		return fieldNumber;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format(
			"%s\n%c %c %c\n%c %c %c\n%c %c %c",
			isGameOver() ? "Game Over" : "Make move for " + getActivePlayer(),
			gameState[0],
			gameState[1],
			gameState[2],
			gameState[3],
			gameState[4],
			gameState[5],
			gameState[6],
			gameState[7],
			gameState[8]
		);
	}
}

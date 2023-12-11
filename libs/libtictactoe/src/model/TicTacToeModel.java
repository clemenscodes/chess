package model;

public class TicTacToeModel {

	/* The default field entry for empty fields */
	public final char EMPTY = '_';
	/* The mark of player 1 on the playing field */
	public final char PLAYER_1 = 'X';
	/* The mark of player 2 on the playing field */
	public final char PLAYER_2 = 'O';

	/* Playing field with 9 entries */
	private char[] gameState;

	/* Counts the moves. Even moves are for PLAYER_1; odd moves are for PLAYER_2. */
	private char moveCount = 0;

	public char[] getBoard() {
		return this.gameState;
	}

	public static void main(String[] args) {
		var game = new TicTacToeModel();
		game.move(1);
		game.move(2);
		game.move(4);
		System.out.println("Field 4 is: " + game.getFieldEntry(4));
	}

	public TicTacToeModel() {
		newGame();
	}

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

	public char getFieldEntry(int field) {
		if (isValidFieldIndex(field)) {
			return gameState[field];
		}
		throw new IndexOutOfBoundsException("Valid fields are [0, 8]");
	}

	private boolean isValidFieldIndex(int field) {
		return field >= 0 && field < gameState.length;
	}

	public boolean isEmptyField(int field) {
		if (isValidFieldIndex(field)) {
			return gameState[field] == EMPTY;
		}
		throw new IndexOutOfBoundsException("Valid fields are [0, 8]");
	}

	public char getActivePlayer() {
		return (moveCount % 2 == 0) ? PLAYER_1 : PLAYER_2;
	}

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

	public boolean isGameOver() {
		return (
			hasPlayer1Won() || hasPlayer2Won() || moveCount >= gameState.length
		);
	}

	public boolean hasPlayer1Won() {
		return checkWinCondition(PLAYER_1);
	}

	public boolean hasPlayer2Won() {
		return checkWinCondition(PLAYER_2);
	}

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

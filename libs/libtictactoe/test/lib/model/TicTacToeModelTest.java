package lib.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TicTacToeModelTest {

	@Test
	void shouldInitialize() {
		var model = new TicTacToeModel();
		var board = model.getBoard();
		assertArrayEquals(
			board,
			new char[] { '_', '_', '_', '_', '_', '_', '_', '_', '_' }
		);
	}

	@Test
	void shouldStartNewGame() {
		var model = new TicTacToeModel();
		model.move(1);
		model.move(2);
		model.newGame();
		var board = model.getBoard();
		assertArrayEquals(
			board,
			new char[] { '_', '_', '_', '_', '_', '_', '_', '_', '_' }
		);
	}

	@Test
	void shouldMakeMoves() {
		var model = new TicTacToeModel();
		model.move(1);
		assertEquals('X', model.getFieldEntry(1));
		assertEquals('O', model.getActivePlayer());
	}

	@Test
	void shouldNotAllowInvalidMoves() {
		var model = new TicTacToeModel();
		assertThrows(IndexOutOfBoundsException.class, () -> model.move(9));
		model.move(1);
		assertThrows(RuntimeException.class, () -> model.move(1));
	}

	@Test
	void shouldCheckGameOver() {
		var model = new TicTacToeModel();
		assertFalse(model.isGameOver());
		model.move(0);
		model.move(1);
		model.move(2);
		model.move(4);
		model.move(3);
		model.move(5);
		model.move(7);
		model.move(6);
		model.move(8);
		assertTrue(model.isGameOver());
	}

	@Test
	void shouldDetectWinner() {
		var model = new TicTacToeModel();
		model.move(0);
		model.move(3);
		model.move(1);
		model.move(4);
		model.move(2);
		assertTrue(model.hasPlayer1Won());
		assertFalse(model.hasPlayer2Won());
	}

	@Test
	void shouldGetFieldFromCoordinates() {
		var model = new TicTacToeModel();
		assertEquals(0, model.getFieldFromCoordinates(100, 200, 900));
		assertEquals(1, model.getFieldFromCoordinates(400, 100, 900));
		assertEquals(2, model.getFieldFromCoordinates(800, 100, 900));
		assertEquals(3, model.getFieldFromCoordinates(200, 400, 900));
		assertEquals(4, model.getFieldFromCoordinates(400, 400, 900));
		assertEquals(5, model.getFieldFromCoordinates(800, 400, 900));
		assertEquals(6, model.getFieldFromCoordinates(200, 800, 900));
		assertEquals(7, model.getFieldFromCoordinates(400, 800, 900));
		assertEquals(8, model.getFieldFromCoordinates(800, 800, 900));
	}

	@Test
	void shouldThrowIndexOutOfBoundsExceptionForInvalidIndexInGetFieldEntry() {
		var model = new TicTacToeModel();
		assertThrows(
			IndexOutOfBoundsException.class,
			() -> model.getFieldEntry(-1)
		);
		assertThrows(
			IndexOutOfBoundsException.class,
			() -> model.getFieldEntry(9)
		);
	}

	@Test
	void shouldThrowIndexOutOfBoundsExceptionForInvalidIndexInIsEmptyField() {
		var model = new TicTacToeModel();
		assertThrows(
			IndexOutOfBoundsException.class,
			() -> model.isEmptyField(-1)
		);
		assertThrows(
			IndexOutOfBoundsException.class,
			() -> model.isEmptyField(9)
		);
	}

	@Test
	void shouldThrowRuntimeExceptionForMoveAfterGameOver() {
		var model = new TicTacToeModel();
		model.move(0);
		model.move(1);
		model.move(2);
		model.move(4);
		model.move(3);
		model.move(5);
		model.move(7);
		model.move(6);
		model.move(8);
		assertThrows(RuntimeException.class, () -> model.move(1));
	}
}

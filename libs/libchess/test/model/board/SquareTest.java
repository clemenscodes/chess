package model.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SquareTest {

	@Test
	void testGetSquare() {
		for (int i = 0; i < 64; i++) {
			Square square = Square.getSquare(i);
			assertNotNull(square);
			assertEquals(i, Square.getIndex(square));
		}
	}

	@Test
	void testGetIndex() {
		for (int i = 0; i < 64; i++) {
			Square square = Square.getSquare(i);
			assertEquals(i, Square.getIndex(square));
		}
	}

	@Test
	void testGetSquareInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> Square.getSquare(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> Square.getSquare(64));
	}

	@Test
	void testGetIndexInvalidSquare() {
		assertThrows(NullPointerException.class, () -> Square.getIndex(null));
		assertThrows(
			IllegalArgumentException.class,
			() -> Square.getIndex(Square.valueOf("invalid"))
		);
	}
}

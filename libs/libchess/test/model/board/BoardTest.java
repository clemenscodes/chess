package model.board;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import model.fen.ForsythEdwardsNotation;
import org.junit.jupiter.api.Test;

public class BoardTest {

	@Test
	void shouldInitialize() {
		assertDoesNotThrow(() -> new Board());
		assertDoesNotThrow(() -> new Board(new ForsythEdwardsNotation()));
	}
}

package model;

import static api.model.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuietMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		board = new Board();
	}

	@Test
	void shouldToggleBits() {
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			01000010""";
		assertEquals(expected, board.getWhiteKnight().getBitboard().toString());
		new QuietMove(g1, f3, board);
		expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000100
			00000000
			01000000""";
		assertEquals(expected, board.getWhiteKnight().getBitboard().toString());
	}

	@Test
	void shouldPrint() {
		QuietMove move = new QuietMove(g1, f3, board);
		String expected =
			"""
			g1f3""";
		assertEquals(expected, move.toString());
	}
}

package model;

import static api.model.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SinglePawnPushMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		board = new Board();
	}

	@Test
	void shouldSetPawnBit() {
		IPiece piece = board.getWhitePawn();
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			11111111
			00000000""";
		assertEquals(expected, piece.getBitboard().toString());
		new SinglePawnPushMove(e2, e3, board, piece);
		expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00001000
			11110111
			00000000""";
		assertEquals(expected, piece.getBitboard().toString());
	}

	@Test
	void shouldPrint() {
		IPiece piece = board.getWhitePawn();
		SinglePawnPushMove move = new SinglePawnPushMove(e2, e3, board, piece);
		assertEquals("e2e3", move.toString());
	}
}

package model;

import static api.model.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoublePawnPushMoveTest {

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
		new DoublePawnPushMove(e2, e4, board, piece);
		expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00001000
			00000000
			11110111
			00000000""";
		assertEquals(expected, piece.getBitboard().toString());
	}

	@Test
	void shouldSetWhiteEnPassantTargetSquare() {
		IPiece piece = board.getWhitePawn();
		new DoublePawnPushMove(e2, e4, board, piece);
		String expected = "e3";
		assertEquals(expected, board.getFen().getEnPassant());
	}

	@Test
	void shouldSetBlackEnPassantTargetSquare() {
		IPiece whitePiece = board.getWhitePawn();
		new DoublePawnPushMove(e2, e4, board, whitePiece);
		IPiece blackPiece = board.getBlackPawn();
		board.getFen().switchActiveColor();
		new DoublePawnPushMove(e7, e5, board, blackPiece);
		String expected = "e6";
		assertEquals(expected, board.getFen().getEnPassant());
	}

	@Test
	void shouldPrint() {
		IPiece piece = board.getWhitePawn();
		DoublePawnPushMove move = new DoublePawnPushMove(e2, e4, board, piece);
		assertEquals("e2e4", move.toString());
	}
}

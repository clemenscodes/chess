package model.move.irreversible.pawn;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import model.board.Board;
import model.board.IBoard;
import model.piece.IPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		board = new Board();
	}

	@Test
	void shouldUnsetPawnBit() {
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
}

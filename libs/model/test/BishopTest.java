import static api.model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import api.model.bits.IBitboard;
import api.model.board.IBoard;
import api.model.piece.Pieces;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BishopTest {

	static class PieceMock extends Bishop {

		public PieceMock(Pieces variant, IBitboard board) {
			super(variant, board);
		}
	}

	private Bishop piece;
	private IBoard board;

	@BeforeEach
	void setup() {
		board = new Board(new ForsythEdwardsNotation("8/8/8/8/8/8/8/8 w - - 0 1"));
		piece = new PieceMock(Pieces.BlackBishop, Bitboard.getSingleBit(Board.getIndex(e4)));
	}

	@Test
	void shouldGetAttacksFromCenter() {
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			10000000
			01000001
			00100010
			00010100
			00000000
			00010100
			00100010
			01000001""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromBottomLeft() {
		piece = new PieceMock(Pieces.BlackBishop, Bitboard.getSingleBit(Board.getIndex(a1)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			00000001
			00000010
			00000100
			00001000
			00010000
			00100000
			01000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromBottomRight() {
		piece = new PieceMock(Pieces.BlackBishop, Bitboard.getSingleBit(Board.getIndex(h1)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			10000000
			01000000
			00100000
			00010000
			00001000
			00000100
			00000010
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromTopLeft() {
		piece = new PieceMock(Pieces.BlackBishop, Bitboard.getSingleBit(Board.getIndex(a8)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			00000000
			01000000
			00100000
			00010000
			00001000
			00000100
			00000010
			00000001""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromTopRight() {
		piece = new PieceMock(Pieces.BlackBishop, Bitboard.getSingleBit(Board.getIndex(h8)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			00000000
			00000010
			00000100
			00001000
			00010000
			00100000
			01000000
			10000000""";
		assertEquals(expected, attacks.toString());
	}
}

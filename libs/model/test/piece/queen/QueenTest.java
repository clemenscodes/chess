package piece.queen;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import bits.Bitboard;
import board.Board;
import fen.ForsythEdwardsNotation;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.piece.Pieces;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueenTest {

	static class PieceMock extends Queen {

		public PieceMock(Pieces variant, IBitboard board) {
			super(variant, board);
		}
	}

	private Queen piece;
	private IBoard board;

	@BeforeEach
	void setup() {
		board = new Board(new ForsythEdwardsNotation("8/8/8/8/8/8/8/8 w - - 0 1"));
		piece = new PieceMock(Pieces.BlackQueen, Bitboard.getSingleBit(Board.getIndex(e4)));
	}

	@Test
	void shouldGetAttacksFromCenter() {
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			10001000
			01001001
			00101010
			00011100
			11110111
			00011100
			00101010
			01001001""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromBottomLeft() {
		piece = new PieceMock(Pieces.BlackQueen, Bitboard.getSingleBit(Board.getIndex(a1)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			10000001
			10000010
			10000100
			10001000
			10010000
			10100000
			11000000
			01111111""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromBottomRight() {
		piece = new PieceMock(Pieces.BlackQueen, Bitboard.getSingleBit(Board.getIndex(h1)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			10000001
			01000001
			00100001
			00010001
			00001001
			00000101
			00000011
			11111110""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromTopLeft() {
		piece = new PieceMock(Pieces.BlackQueen, Bitboard.getSingleBit(Board.getIndex(a8)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			01111111
			11000000
			10100000
			10010000
			10001000
			10000100
			10000010
			10000001""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromTopRight() {
		piece = new PieceMock(Pieces.BlackQueen, Bitboard.getSingleBit(Board.getIndex(h8)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			11111110
			00000011
			00000101
			00001001
			00010001
			00100001
			01000001
			10000001""";
		assertEquals(expected, attacks.toString());
	}
}

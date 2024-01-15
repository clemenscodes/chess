package model.piece.knight;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.fen.ForsythEdwardsNotation;
import model.piece.Pieces;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KnightTest {

	static class PieceMock extends Knight {

		public PieceMock(Pieces variant, IBitboard board) {
			super(variant, board);
		}
	}

	private Knight piece;
	private IBoard board;

	@BeforeEach
	void setup() {
		board = new Board(new ForsythEdwardsNotation("8/8/8/8/8/8/8/8 w - - 0 1"));
		piece = new PieceMock(Pieces.BlackKnight, Bitboard.getSingleBit(Square.getIndex(e4)));
	}

	@Test
	void shouldGetAttacksFromCenter() {
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			00000000
			00000000
			00010100
			00100010
			00000000
			00100010
			00010100
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromBottomLeft() {
		piece = new PieceMock(Pieces.BlackKnight, Bitboard.getSingleBit(Square.getIndex(a1)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			01000000
			00100000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromBottomRight() {
		piece = new PieceMock(Pieces.BlackKnight, Bitboard.getSingleBit(Square.getIndex(h1)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000010
			00000100
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromTopLeft() {
		piece = new PieceMock(Pieces.BlackKnight, Bitboard.getSingleBit(Square.getIndex(a8)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			00000000
			00100000
			01000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromTopRight() {
		piece = new PieceMock(Pieces.BlackKnight, Bitboard.getSingleBit(Square.getIndex(h8)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			00000000
			00000100
			00000010
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}
}

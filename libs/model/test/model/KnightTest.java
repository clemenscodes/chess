package model;

import static api.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import api.Pieces;
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
		piece = new PieceMock(Pieces.BlackKnight, Bitboard.getSingleBit(Board.getIndex(e4)));
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
		piece = new PieceMock(Pieces.BlackKnight, Bitboard.getSingleBit(Board.getIndex(a1)));
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
		piece = new PieceMock(Pieces.BlackKnight, Bitboard.getSingleBit(Board.getIndex(h1)));
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
		piece = new PieceMock(Pieces.BlackKnight, Bitboard.getSingleBit(Board.getIndex(a8)));
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
		piece = new PieceMock(Pieces.BlackKnight, Bitboard.getSingleBit(Board.getIndex(h8)));
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

	@Test
	void shouldGetAllAttacks() {
		String fen = "rnbqkbnr/pppp1ppp/8/4p3/8/5N2/PPPPPPPP/RNBQKB1R w KQkq e6 0 2";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new PieceMock(Pieces.WhiteKnight, board.getWhiteKnight().getBitboard());
		IBitboard attacks = piece.getAllAttacks(board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00001010
			00010001
			10100000
			00000000
			00000010""";
		assertEquals(expected, attacks.toString());
	}
}

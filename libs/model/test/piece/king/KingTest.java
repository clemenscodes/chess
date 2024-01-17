package piece.king;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import bits.Bitboard;
import board.Board;
import fen.ForsythEdwardsNotation;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Pieces;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KingTest {

	static class PieceMock extends King {

		public PieceMock(Pieces variant, IBitboard board) {
			super(variant, board);
		}
	}

	private King piece;
	private IBoard board;

	@BeforeEach
	void setup() {
		board = new Board(new ForsythEdwardsNotation("8/8/8/8/8/8/8/8 w - - 0 1"));
		piece = new PieceMock(Pieces.BlackKing, Bitboard.getSingleBit(Board.getIndex(e4)));
	}

	@Test
	void shouldGetAttacksFromCenter() {
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00011100
			00010100
			00011100
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromBottomLeft() {
		piece = new PieceMock(Pieces.BlackKing, Bitboard.getSingleBit(Board.getIndex(a1)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			11000000
			01000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromBottomRight() {
		piece = new PieceMock(Pieces.BlackKing, Bitboard.getSingleBit(Board.getIndex(h1)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000011
			00000010""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromTopLeft() {
		piece = new PieceMock(Pieces.BlackKing, Bitboard.getSingleBit(Board.getIndex(a8)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			01000000
			11000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksFromTopRight() {
		piece = new PieceMock(Pieces.BlackKing, Bitboard.getSingleBit(Board.getIndex(h8)));
		IBitboard attacks = piece.getAttacks(piece.getBitboard(), board);
		String expected =
			"""
			00000010
			00000011
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAllAttacks() {
		piece = new PieceMock(Pieces.BlackKing, Bitboard.getSingleBit(Board.getIndex(h8)));
		IBitboard attacks = piece.getAllAttacks(board);
		String expected =
			"""
			00000010
			00000011
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldErrorIfKingCanNotMoveToDestination() {
		board = new Board(new ForsythEdwardsNotation());
		piece = new PieceMock(Pieces.BlackKing, board.getBlackKing().getBitboard());
		int src = Board.getIndex(a2);
		int dst = Board.getIndex(a4);
		try {
			piece.move(src, dst, board);
		} catch (Error e) {
			assertEquals("Invalid move", e.getMessage());
		}
	}

	@Test
	void shouldErrorIfKingUnsafe() {
		String fen = "rn1qkbnr/ppp1pppp/3p4/8/3P2b1/4P3/PPP2PPP/RNBQKBNR w KQkq - 1 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new PieceMock(Pieces.WhiteKing, board.getWhiteKing().getBitboard());
		int src = Board.getIndex(e1);
		int dst = Board.getIndex(e2);
		try {
			piece.move(src, dst, board);
		} catch (Error e) {
			assertEquals("King can not move into an attack", e.getMessage());
		}
	}

	@Test
	void shouldCaptureIfDestinationOverlapsWithEnemy() {
		String fen = "rn1qkbnr/ppp1pppp/3p4/8/3P4/4P3/PPPQbPPP/RNB1KBNR w KQkq - 3 4";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new PieceMock(Pieces.WhiteKing, board.getWhiteKing().getBitboard());
		int src = Board.getIndex(e1);
		int dst = Board.getIndex(e2);
		piece.move(src, dst, board);
	}

	@Test
	void shouldKingCastle() {
		String fen = "r3k2r/pppq1ppp/2np1n2/1Bb1p3/4P1b1/2NPBN2/PPPQ1PPP/R3K2R w KQkq - 4 8";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new PieceMock(Pieces.WhiteKing, board.getWhiteKing().getBitboard());
		int src = Board.getIndex(e1);
		int dst = Board.getIndex(g1);
		piece.move(src, dst, board);
		board.getFen().updatePiecePlacementData(board);
		board.getFen().switchActiveColor();
		String expected = "r3k2r/pppq1ppp/2np1n2/1Bb1p3/4P1b1/2NPBN2/PPPQ1PPP/R4RK1 b kq - 5 8";
		assertEquals(expected, board.getFen().toString());
	}

	@Test
	void shouldQueenCastle() {
		String fen = "r3k2r/pppq1ppp/2np1n2/1Bb1p3/4P1b1/2NPBN2/PPPQ1PPP/R3K2R w KQkq - 4 8";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new PieceMock(Pieces.WhiteKing, board.getWhiteKing().getBitboard());
		int src = Board.getIndex(e1);
		int dst = Board.getIndex(c1);
		piece.move(src, dst, board);
		board.getFen().updatePiecePlacementData(board);
		board.getFen().switchActiveColor();
		String expected = "r3k2r/pppq1ppp/2np1n2/1Bb1p3/4P1b1/2NPBN2/PPPQ1PPP/2KR3R b kq - 5 8";
		assertEquals(expected, board.getFen().toString());
	}

	@Test
	void shouldErrorIfInvalidKingCastle() {
		String fen = "r3k2r/pppq1ppp/2np1n2/1Bb1p3/4P1b1/2NPBN2/PPPQ1PPP/R3K2R w - - 4 8";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new PieceMock(Pieces.WhiteKing, board.getWhiteKing().getBitboard());
		int src = Board.getIndex(e1);
		int dst = Board.getIndex(g1);
		try {
			piece.move(src, dst, board);
		} catch (Error e) {
			assertEquals("Invalid move", e.getMessage());
		}
	}

	@Test
	void shouldErrorIfInvalidQueenCastle() {
		String fen = "r3k2r/pppq1ppp/2np1n2/1Bb1p3/4P1b1/2NPBN2/PPPQ1PPP/R3K2R w - - 4 8";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new PieceMock(Pieces.WhiteKing, board.getWhiteKing().getBitboard());
		int src = Board.getIndex(e1);
		int dst = Board.getIndex(c1);
		try {
			piece.move(src, dst, board);
		} catch (Error e) {
			assertEquals("Invalid move", e.getMessage());
		}
	}
}

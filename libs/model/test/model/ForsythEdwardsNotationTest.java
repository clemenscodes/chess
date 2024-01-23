package model;

import static api.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ForsythEdwardsNotationTest {

	@Test
	void shouldGetIsWhite() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertTrue(fen.isWhite());
	}

	@Test
	void testDefaultConstructor() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertNotNull(fen);
		assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", fen.toString());
	}

	@Test
	void testConstructor() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation(
			"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
		);
		assertNotNull(fen);
		assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", fen.toString());
	}

	@Test
	void testParseValidFEN() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq e3 5 10");
		assertEquals(
			"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",
			String.join("/", fen.getPiecePlacementData())
		);
		assertEquals('b', fen.getActiveColor());
		assertEquals("KQkq", fen.getCastling());
		assertEquals("e3", fen.getEnPassant());
		assertEquals(5, fen.getHalfMoveClock());
		assertEquals(10, fen.getFullMoveNumber());
	}

	@Test
	void testParseInvalidFEN() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertThrows(IllegalArgumentException.class, () -> fen.parse("Invalid FEN"));
	}

	@Test
	void testIncrementFullMoveNumber() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.incrementFullMoveNumber();
		assertEquals(2, fen.getFullMoveNumber());
	}

	@Test
	void testIncrementHalfMoveClock() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.incrementHalfMoveClock();
		assertEquals(1, fen.getHalfMoveClock());
	}

	@Test
	void testResetHalfMoveClock() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.incrementHalfMoveClock();
		fen.resetHalfMoveClock();
		assertEquals(0, fen.getHalfMoveClock());
	}

	@Test
	void testSwitchActiveColor() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.switchActiveColor();
		assertEquals('b', fen.getActiveColor());
		fen.switchActiveColor();
		assertEquals('w', fen.getActiveColor());
	}

	@Test
	void testSetEnPassantTargetSquare() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.setEnPassantTargetSquare(e3);
		assertEquals("e3", fen.getEnPassant());
		assertNotNull(fen.getEnPassantMask());
		assertEquals(fen.getEnPassantMask(), Bitboard.getSingleBit(Board.getIndex(e3)));
	}

	@Test
	void testUnsetEnPassantTargetSquare() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.setEnPassantTargetSquare(e3);
		fen.unsetEnPassantTargetSquare();
		assertEquals("-", fen.getEnPassant());
		assertNotNull(fen.getEnPassantMask());
		assertEquals(fen.getEnPassantMask().getBits(), new Bitboard().getBits());
	}

	@Test
	void testCastle() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.castle();
		assertFalse(fen.getWhiteKingCastle());
		assertFalse(fen.getWhiteQueenCastle());
		assertTrue(fen.getBlackKingCastle());
		assertTrue(fen.getBlackQueenCastle());
		fen.switchActiveColor();
		fen.castle();
		assertFalse(fen.getBlackKingCastle());
		assertFalse(fen.getBlackQueenCastle());
	}

	@Test
	void testKingMove() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.kingMove();
		assertFalse(fen.getWhiteKingCastle());
		assertFalse(fen.getWhiteQueenCastle());
		assertTrue(fen.getBlackKingCastle());
		assertTrue(fen.getBlackQueenCastle());
		fen.switchActiveColor();
		fen.kingMove();
		assertFalse(fen.getBlackKingCastle());
		assertFalse(fen.getBlackQueenCastle());
	}

	@Test
	void testKingRookMove() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.kingRookMove();
		assertFalse(fen.getWhiteKingCastle());
		assertTrue(fen.getWhiteQueenCastle());
		assertTrue(fen.getBlackKingCastle());
		assertTrue(fen.getBlackQueenCastle());
		fen.switchActiveColor();
		fen.kingRookMove();
		assertFalse(fen.getBlackKingCastle());
		assertTrue(fen.getBlackQueenCastle());
	}

	@Test
	void testQueenRookMove() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.queenRookMove();
		assertTrue(fen.getWhiteKingCastle());
		assertFalse(fen.getWhiteQueenCastle());
		assertTrue(fen.getBlackKingCastle());
		assertTrue(fen.getBlackQueenCastle());
		fen.switchActiveColor();
		fen.queenRookMove();
		assertTrue(fen.getBlackKingCastle());
		assertFalse(fen.getBlackQueenCastle());
	}

	@Test
	void testUpdatePiecePlacementData() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation(
			"rnbqkbnr/1ppppppp/8/8/3PP3/2N2N2/PpPB1PPP/R2QKB1R b KQkq - 1 5"
		);
		IBoard board = new Board();
		fen.updatePiecePlacementData(board);
		String expected = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 1 5";
		assertEquals(expected, fen.toString());
	}

	@Test
	void testInvalidPiecePlacementData() {
		ForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
		);
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPPPPPPP/RNBQKBNR w KQkq - 0 1")
		);
	}

	@Test
	void testInvalidActiveColor() {
		ForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR x KQkq - 0 1")
		);
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR  KQkq - 0 1")
		);
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR KQkq - 0 1")
		);
	}

	@Test
	void testInvalidEnPassantInfo() {
		ForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq x 0 1")
		);
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq e 0 1")
		);
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq e99 0 1")
		);
	}

	@Test
	void testInvalidHalfMoveClockNegative() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - -1 1")
		);
	}

	@Test
	void testInvalidHalfMoveClockNumberFormat() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - x 1")
		);
	}

	@Test
	void testInvalidHalfMoveClockExceedsMax() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 151 1")
		);
	}

	@Test
	void testInvalidFullMoveNumberZero() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0")
		);
	}

	@Test
	void testInvalidFullMoveNumberNegative() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - -5 1")
		);
	}

	@Test
	void testInvalidFullMoveClockNumberFormat() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 x")
		);
	}

	@Test
	void testInvalidCastlingInfo() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		assertThrows(
			IllegalArgumentException.class,
			() -> fen.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkqz - 1 1")
		);
	}
}

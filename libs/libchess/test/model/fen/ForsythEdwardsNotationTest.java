package model.fen;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import model.bits.Bitboard;
import model.board.Square;
import org.junit.jupiter.api.Test;

public class ForsythEdwardsNotationTest {

	@Test
	void testDefaultConstructor() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
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
		assertEquals(fen.getEnPassantMask(), Bitboard.getSingleBit(Square.getIndex(e3)));
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
	}

	@Test
	void testKingMove() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.kingMove();
		assertFalse(fen.getWhiteKingCastle());
		assertFalse(fen.getWhiteQueenCastle());
		assertTrue(fen.getBlackKingCastle());
		assertTrue(fen.getBlackQueenCastle());
	}

	@Test
	void testKingRookMove() {
		IForsythEdwardsNotation fen = new ForsythEdwardsNotation();
		fen.kingRookMove();
		assertFalse(fen.getWhiteKingCastle());
		assertTrue(fen.getWhiteQueenCastle());
		assertTrue(fen.getBlackKingCastle());
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
	}
}

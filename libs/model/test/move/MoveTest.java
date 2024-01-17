package move;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import bits.Bitboard;
import board.Board;
import fen.ForsythEdwardsNotation;
import model.board.IBoard;
import model.board.Square;
import model.fen.IForsythEdwardsNotation;
import move.irreversible.pawn.EnPassantCaptureMove;
import move.reversible.QuietMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveTest {

	private IBoard board;

	@BeforeEach
	void initBoard() {
		board = new Board();
	}

	@Test
	void shouldIncrementFullMoveNumber() {
		IForsythEdwardsNotation fen = board.getFen();
		int fullMoveNumber = fen.getFullMoveNumber();
		assertEquals(1, fullMoveNumber);
		new QuietMove(g1, f3, board);
		fen.switchActiveColor();
		fullMoveNumber = fen.getFullMoveNumber();
		assertEquals(1, fullMoveNumber);
		new QuietMove(g8, f6, board);
		fen.switchActiveColor();
		fullMoveNumber = fen.getFullMoveNumber();
		assertEquals(2, fullMoveNumber);
		new QuietMove(b1, c3, board);
		fen.switchActiveColor();
		fullMoveNumber = fen.getFullMoveNumber();
		assertEquals(2, fullMoveNumber);
	}

	@Test
	void shouldDeterminePromotion() {
		boolean result = Move.isPromotion(Bitboard.merge(Board.firstRank, Board.eighthRank));
		assertTrue(result);
	}

	@Test
	void shouldDetermineCapture() {
		boolean result = Move.isCapture(Bitboard.getSingleBit(Board.getIndex(e2)), board);
		assertFalse(result);
		result = Move.isCapture(Bitboard.getSingleBit(Board.getIndex(e7)), board);
		assertTrue(result);
	}

	@Test
	void shouldDetermineEnPassant() {
		String fen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		boolean result = Move.isEnPassant(Bitboard.getSingleBit(Board.getIndex(e3)), board);
		assertTrue(result);
	}

	@Test
	void shouldDetermineIfWhiteKingCastle() {
		String fen = "r1bqk1nr/pppp1ppp/2nb4/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		boolean result = Move.isKingCastle(e1, g1, board);
		assertTrue(result);
	}

	@Test
	void shouldDetermineIfBlackKingCastle() {
		String fen = "rnbqk2r/pppp1ppp/5n2/2b1p3/4P3/2N2N2/PPPP1PPP/R1BQKB1R b KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		boolean result = Move.isKingCastle(e8, g8, board);
		assertTrue(result);
	}

	@Test
	void shouldDetermineIfWhiteQueenCastle() {
		String fen = "r3kbnr/pppqpppp/2n5/3p1b2/3P1B2/2N5/PPPQPPPP/R3KBNR w KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		boolean result = Move.isQueenCastle(e1, c1, board);
		assertTrue(result);
	}

	@Test
	void shouldDetermineIfBlackQueenCastle() {
		String fen = "r3kbnr/pppqpppp/2n5/3p1b2/3P1B2/2N5/PPPQPPPP/R3KBNR b KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		boolean result = Move.isQueenCastle(e8, c8, board);
		assertTrue(result);
	}

	@Test
	void shouldDetermineIfWhiteCanKingCastle() {
		String fen = "r1bqk1nr/pppp1ppp/2nb4/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		boolean result = Move.canKingCastle(e1, g1, board);
		assertTrue(result);
	}

	@Test
	void shouldDetermineIfBlackCanKingCastle() {
		String fen = "rnbqk2r/pppp1ppp/5n2/2b1p3/4P3/2N2N2/PPPP1PPP/R1BQKB1R b KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		boolean result = Move.canKingCastle(e8, g8, board);
		assertTrue(result);
	}

	@Test
	void shouldDetermineIfWhiteCanQueenCastle() {
		String fen = "r3kbnr/pppqpppp/2n5/3p1b2/3P1B2/2N5/PPPQPPPP/R3KBNR w KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		boolean result = Move.canQueenCastle(e1, c1, board);
		assertTrue(result);
	}

	@Test
	void shouldDetermineIfBlackCanQueenCastle() {
		String fen = "r3kbnr/pppqpppp/2n5/3p1b2/3P1B2/2N5/PPPQPPPP/R3KBNR b KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		boolean result = Move.canQueenCastle(e8, c8, board);
		assertTrue(result);
	}

	@Test
	void testValidMoveInitialization() {
		Move move = new QuietMove(g1, f3, board);
		assertEquals(g1, move.getSource());
		assertEquals(f3, move.getDestination());
	}

	@Test
	void testCanNotMoveOpponentsPieces() {
		assertThrows(Error.class, () -> new QuietMove(e7, e5, board));
		try {
			new QuietMove(e7, e5, board);
		} catch (Error e) {
			assertEquals(e.getMessage(), "Can not move opponent's piece");
		}
	}

	@Test
	void testCanHandleEnPassant() {
		String fen = "rnbqkb1r/ppp1pppp/5n2/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		new EnPassantCaptureMove(e5, d6, board, board.getPiece(e5));
	}

	@Test
	void testCanMakeKingRookMove() {
		String fen = "rnbqkb1r/pppppppp/5n2/8/8/5N2/PPPPPPPP/RNBQKB1R w KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		new QuietMove(h1, g1, board);
		board.getFen().switchActiveColor();
		new QuietMove(h8, g8, board);
		assertEquals("Qq", board.getFen().getCastling());
	}

	@Test
	void testCanMakeQueenRookMove() {
		String fen = "r1bqkbnr/pppppppp/2n5/8/8/2N5/PPPPPPPP/R1BQKBNR w KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		new QuietMove(a1, b1, board);
		board.getFen().switchActiveColor();
		new QuietMove(a8, b8, board);
		assertEquals("Kk", board.getFen().getCastling());
	}
}

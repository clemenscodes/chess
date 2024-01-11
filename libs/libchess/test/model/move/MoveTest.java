package model.move;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import model.bits.Bitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.fen.ForsythEdwardsNotation;
import model.move.irreversible.pawn.DoublePawnPushMove;
import model.move.reversible.QuietMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveTest {

	private IBoard board;

	@BeforeEach
	void initBoard() {
		board = new Board();
	}

	@Test
	void shouldDeterminePromotion() {
		boolean result = Move.isPromotion(Bitboard.merge(Board.firstRank, Board.eighthRank));
		assertTrue(result);
	}

	@Test
	void shouldDetermineCapture() {
		boolean result = Move.isCapture(Bitboard.getSingleBit(Square.getIndex(e2)), board);
		assertTrue(result);
	}

	@Test
	void shouldDetermineEnPassant() {
		String fen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		boolean result = Move.isEnPassant(Bitboard.getSingleBit(Square.getIndex(e3)), board);
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
}

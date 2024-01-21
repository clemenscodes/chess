package model;

import static api.model.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveListTest {

	public BlockingQueue<String> queue;
	private IReader<String> reader;
	private IMoveList list;
	private IBoard board;

	@BeforeEach
	void init() {
		list = new MoveList();
		board = new Board();
		queue = new LinkedBlockingQueue<>();
		reader = new Reader<>(queue);
	}

	@Test
	void shouldInitializeCorrectly() {
		int expectedPlayedMoves = 0;
		assertEquals(expectedPlayedMoves, list.getPlayedMoves());
	}

	@Test
	void shouldNotMoveToSourceSquare() {
		try {
			list.makeMove(e2, e2, board, reader);
		} catch (IllegalArgumentException e) {
			assertEquals("Source and destination must be different", e.getMessage());
		}
		assertEquals(0, list.getPlayedMoves());
	}

	@Test
	void shouldPrintMoveList() {
		list.makeMove(e2, e4, board, reader);
		list.makeMove(e7, e5, board, reader);
		list.makeMove(g1, f3, board, reader);
		list.makeMove(b8, c6, board, reader);
		String expected =
			"""
			1. e2e4 e7e5
			2. g1f3 b8c6
			""";
		assertEquals(expected, list.toString());
	}

	@Test
	void shouldMakeWhitePawnMove() {
		list.makeMove(e2, e4, board, reader);
	}

	@Test
	void shouldMakeBlackPawnMove() {
		String fen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		list.makeMove(e7, e5, board, reader);
	}

	@Test
	void shouldMakeWhiteKnightMove() {
		String fen = "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		list.makeMove(g1, f3, board, reader);
	}

	@Test
	void shouldMakeBlackKnightMove() {
		String fen = "rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		list.makeMove(b8, c6, board, reader);
	}

	@Test
	void shouldMakeWhiteBishopMove() {
		String fen = "r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		list.makeMove(f1, b5, board, reader);
	}

	@Test
	void shouldMakeBlackBishopMove() {
		String fen = "r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		list.makeMove(f8, c5, board, reader);
	}

	@Test
	void shouldMakeWhiteKingMove() {
		String fen = "r1bqk1nr/pppp1ppp/2n5/1Bb1p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		list.makeMove(e1, g1, board, reader);
	}

	@Test
	void shouldMakeBlackKingMove() {
		String fen = "r1bqk2r/pppp1ppp/2n2n2/1Bb1p3/4P3/3P1N2/PPP2PPP/RNBQ1RK1 b kq - 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		list.makeMove(e8, g8, board, reader);
	}

	@Test
	void shouldMakeWhiteQueenMove() {
		String fen = "r2q1rk1/ppp2ppp/2npbn2/1Bb1p1B1/4P3/2NP1N2/PPP2PPP/R2Q1RK1 w - - 2 8";
		board = new Board(new ForsythEdwardsNotation(fen));
		list.makeMove(d1, d2, board, reader);
	}

	@Test
	void shouldMakeBlackQueenMove() {
		String fen = "r2q1rk1/ppp2ppp/2npbn2/1Bb1p1B1/4P3/2NP1N2/PPPQ1PPP/R4RK1 b - - 3 8";
		board = new Board(new ForsythEdwardsNotation(fen));
		list.makeMove(d8, d7, board, reader);
	}

	@Test
	void shouldMakeWhiteRookMove() {
		String fen = "r4rk1/pppq1ppp/2npbn2/1Bb1p1B1/4P3/2NP1N2/PPPQ1PPP/R4RK1 w - - 4 9";
		board = new Board(new ForsythEdwardsNotation(fen));
		list.makeMove(a1, d1, board, reader);
	}

	@Test
	void shouldMakeBlackRookMove() {
		String fen = "r4rk1/pppq1ppp/2npbn2/1Bb1p1B1/4P3/2NP1N2/PPPQ1PPP/3R1RK1 b - - 5 9";
		board = new Board(new ForsythEdwardsNotation(fen));
		list.makeMove(a8, d8, board, reader);
	}
}

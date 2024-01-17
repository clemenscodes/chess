package model;

import static api.model.Square.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import api.model.IBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CastleMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		board = new Board();
	}

	@Test
	void shouldNotCastleFromCheck() {
		String fen = "r1bqk2r/pppp1ppp/2n2n2/1B2p3/1b2P3/3P1N2/PPP2PPP/RNBQK2R w KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		try {
			new KingCastleMove(e1, g1, board);
		} catch (Error e) {
			assertEquals("Can not castle when king is in check", e.getMessage());
		}
	}

	@Test
	void shouldNotCastleIfSquaresNotSafeAndEmpty() {
		String fen = "r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		try {
			new KingCastleMove(e1, g1, board);
		} catch (Error e) {
			assertEquals(
				"Squares the king walks while castling must be safe and empty",
				e.getMessage()
			);
		}
	}

	@Test
	void shouldMovePieces() {
		String fen = "r1bqk2r/pppp1ppp/2n2n2/1Bb1p3/4P3/3P1N2/PPP2PPP/RNBQK2R w KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		String expected =
			"""
			[♜] [ ] [♝] [♛] [♚] [ ] [ ] [♜]
			[♟] [♟] [♟] [♟] [ ] [♟] [♟] [♟]
			[ ] [ ] [♞] [ ] [ ] [♞] [ ] [ ]
			[ ] [♗] [♝] [ ] [♟] [ ] [ ] [ ]
			[ ] [ ] [ ] [ ] [♙] [ ] [ ] [ ]
			[ ] [ ] [ ] [♙] [ ] [♘] [ ] [ ]
			[♙] [♙] [♙] [ ] [ ] [♙] [♙] [♙]
			[♖] [♘] [♗] [♕] [♔] [ ] [ ] [♖]""";
		assertEquals(expected, board.toString());
		new KingCastleMove(e1, g1, board);
		expected =
			"""
			[♜] [ ] [♝] [♛] [♚] [ ] [ ] [♜]
			[♟] [♟] [♟] [♟] [ ] [♟] [♟] [♟]
			[ ] [ ] [♞] [ ] [ ] [♞] [ ] [ ]
			[ ] [♗] [♝] [ ] [♟] [ ] [ ] [ ]
			[ ] [ ] [ ] [ ] [♙] [ ] [ ] [ ]
			[ ] [ ] [ ] [♙] [ ] [♘] [ ] [ ]
			[♙] [♙] [♙] [ ] [ ] [♙] [♙] [♙]
			[♖] [♘] [♗] [♕] [ ] [♖] [♔] [ ]""";
		assertEquals(expected, board.toString());
	}
}

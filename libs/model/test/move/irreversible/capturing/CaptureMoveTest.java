package model.move.irreversible.capturing;

import static model.board.Square.c3;
import static model.board.Square.d5;
import static org.junit.jupiter.api.Assertions.*;

import model.board.Board;
import model.board.IBoard;
import model.fen.ForsythEdwardsNotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CaptureMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		board = new Board();
	}

	@Test
	void shouldCapturePiece() {
		String fen = "rnbqkb1r/ppp1pppp/5n2/3p4/8/2N2N2/PPPPPPPP/R1BQKB1R w KQkq - 2 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		String expected =
			"""
			[♜] [♞] [♝] [♛] [♚] [♝] [ ] [♜]
			[♟] [♟] [♟] [ ] [♟] [♟] [♟] [♟]
			[ ] [ ] [ ] [ ] [ ] [♞] [ ] [ ]
			[ ] [ ] [ ] [♟] [ ] [ ] [ ] [ ]
			[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
			[ ] [ ] [♘] [ ] [ ] [♘] [ ] [ ]
			[♙] [♙] [♙] [♙] [♙] [♙] [♙] [♙]
			[♖] [ ] [♗] [♕] [♔] [♗] [ ] [♖]""";
		assertEquals(expected, board.toString());
		new CaptureMove(c3, d5, board);
		expected =
			"""
			[♜] [♞] [♝] [♛] [♚] [♝] [ ] [♜]
			[♟] [♟] [♟] [ ] [♟] [♟] [♟] [♟]
			[ ] [ ] [ ] [ ] [ ] [♞] [ ] [ ]
			[ ] [ ] [ ] [♘] [ ] [ ] [ ] [ ]
			[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
			[ ] [ ] [ ] [ ] [ ] [♘] [ ] [ ]
			[♙] [♙] [♙] [♙] [♙] [♙] [♙] [♙]
			[♖] [ ] [♗] [♕] [♔] [♗] [ ] [♖]""";
		assertEquals(expected, board.toString());
	}

	@Test
	void shouldPrint() {
		String fen = "rnbqkb1r/ppp1pppp/5n2/3p4/8/2N2N2/PPPPPPPP/R1BQKB1R w KQkq - 2 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		CaptureMove move = new CaptureMove(c3, d5, board);
		assertEquals("c3d5", move.toString());
	}
}

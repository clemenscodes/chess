package move.irreversible.pawn;

import static api.model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import api.model.board.IBoard;
import api.model.piece.IPiece;
import board.Board;
import fen.ForsythEdwardsNotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EnPassantCaptureMoveTest {

	private IBoard board;
	private IPiece piece;

	@BeforeEach
	void init() {
		String fen = "rnbqkb1r/ppp1pppp/5n2/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = board.getWhitePawn();
	}

	@Test
	void shouldSetPawnBit() {
		String expected =
			"""
			00000000
			00000000
			00000000
			00001000
			00000000
			00000000
			11110111
			00000000""";
		assertEquals(expected, piece.getBitboard().toString());
		new EnPassantCaptureMove(e5, d6, board, piece);
		expected =
			"""
			00000000
			00000000
			00010000
			00000000
			00000000
			00000000
			11110111
			00000000""";
		assertEquals(expected, piece.getBitboard().toString());
	}

	@Test
	void shouldPrint() {
		EnPassantCaptureMove move = new EnPassantCaptureMove(e5, d6, board, piece);
		assertEquals("e5d6", move.toString());
	}
}

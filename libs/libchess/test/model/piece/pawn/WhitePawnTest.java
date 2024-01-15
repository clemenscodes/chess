package model.piece.pawn;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.fen.ForsythEdwardsNotation;
import model.reader.IReader;
import model.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WhitePawnTest {

	private IReader reader;
	private Pawn piece;
	private IBoard board;

	@BeforeEach
	void setup() {
		String inputString = "Q";
		byte[] bytes = inputString.getBytes(StandardCharsets.UTF_8);
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		board = new Board(new ForsythEdwardsNotation("8/8/8/8/8/8/8/8 w - - 0 1"));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
	}

	@Test
	void shouldErrorIfSourceSquareDoesNotHavePiece() {
		int src = Square.getIndex(a1);
		int dst = Square.getIndex(b1);
		try {
			piece.move(src, dst, board, reader);
		} catch (Error e) {
			assertEquals("Invalid move", e.getMessage());
		}
	}

	@Test
	void shouldErrorIfPieceCanNotMoveToDestination() {
		board = new Board();
		int src = Square.getIndex(a2);
		int dst = Square.getIndex(a3);
		try {
			piece.move(src, dst, board, reader);
		} catch (Error e) {
			assertEquals("Invalid move", e.getMessage());
		}
	}
}

package model.move;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import model.board.Board;
import model.board.IBoard;
import model.reader.IReader;
import model.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveListTest {

	private IMoveList list;
	private IBoard board;
	private IReader reader;

	@BeforeEach
	void init() {
		list = new MoveList();
		board = new Board();
		String input = "Q";
		byte[] buf = input.getBytes(StandardCharsets.UTF_8);
		InputStream is = new ByteArrayInputStream(buf);
		reader = new Reader(is);
	}

	@Test
	void shouldInitializeCorrectly() {
		int expectedPlayedMoves = 0;
		assertEquals(expectedPlayedMoves, list.getMoves().size());
	}

	@Test
	void shouldMakeMove() {
		list.makeMove(e2, e4, board, reader);
		assertEquals(1, list.getMoves().size());
	}

	@Test
	void shouldNotMoveToSourceSquare() {
		try {
			list.makeMove(e2, e2, board, reader);
		} catch (IllegalArgumentException e) {
			assertEquals("Source and destination must be different", e.getMessage());
		}
		assertEquals(0, list.getMoves().size());
	}
}

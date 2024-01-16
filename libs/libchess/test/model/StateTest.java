package model;

import static org.junit.jupiter.api.Assertions.*;

import model.board.Board;
import model.board.IBoard;
import model.fen.ForsythEdwardsNotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StateTest {

	private IBoard board;

	@BeforeEach
	void setup() {
		String fen = "rnb1kbnr/pppp1ppp/8/4p3/6Pq/5P2/PPPPP2P/RNBQKBNR w KQkq - 1 3";
		board = new Board(new ForsythEdwardsNotation(fen));
	}

	@Test
	void testEnumValues() {
		State[] states = State.values();
		assertEquals(3, states.length);
		assertEquals(State.Start, states[0]);
		assertEquals(State.Playing, states[1]);
		assertEquals(State.GameOver, states[2]);
	}

	@Test
	void testEnumToString() {
		assertEquals("Start", State.Start.toString());
		assertEquals("Playing", State.Playing.toString());
		assertEquals("GameOver", State.GameOver.toString());
	}

	@Test
	void testEnumValueOf() {
		assertEquals(State.Start, State.valueOf("Start"));
		assertEquals(State.Playing, State.valueOf("Playing"));
		assertEquals(State.GameOver, State.valueOf("GameOver"));
	}

	@Test
	void shouldDetermineNonCheckmate() {
		assertFalse(State.isCheckmate(new Board()));
	}

	@Test
	void shouldDetermineCheckmate() {
		assertTrue(State.isCheckmate(board));
	}

	@Test
	void shouldDetermineStalemate() {
		String fen = "8/8/8/8/8/7k/7p/7K w - - 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		System.out.println(board);
		assertTrue(State.isStalemate(board));
	}
}

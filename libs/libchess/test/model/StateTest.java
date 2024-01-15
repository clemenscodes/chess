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
	void shouldDetermineCheckmate() {
		boolean isMate = State.isCheckmate(board);
		assertTrue(isMate);
	}
}

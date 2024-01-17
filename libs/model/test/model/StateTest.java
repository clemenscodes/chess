package model;

import static org.junit.jupiter.api.Assertions.*;

import api.model.State;
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
		assertEquals(5, states.length);
		assertEquals(State.Start, states[0]);
		assertEquals(State.Playing, states[1]);
		assertEquals(State.Checkmate, states[2]);
		assertEquals(State.Stalemate, states[3]);
		assertEquals(State.GameOver, states[4]);
	}

	@Test
	void testEnumToString() {
		assertEquals("Start", State.Start.toString());
		assertEquals("Playing", State.Playing.toString());
		assertEquals("Checkmate", State.Checkmate.toString());
		assertEquals("Stalemate", State.Stalemate.toString());
		assertEquals("GameOver", State.GameOver.toString());
	}

	@Test
	void testEnumValueOf() {
		assertEquals(State.Start, State.valueOf("Start"));
		assertEquals(State.Playing, State.valueOf("Playing"));
		assertEquals(State.Checkmate, State.valueOf("Checkmate"));
		assertEquals(State.Stalemate, State.valueOf("Stalemate"));
		assertEquals(State.GameOver, State.valueOf("GameOver"));
	}
}

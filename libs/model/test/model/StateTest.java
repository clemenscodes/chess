package model;

import static org.junit.jupiter.api.Assertions.*;

import api.model.State;
import org.junit.jupiter.api.Test;

public class StateTest {

	@Test
	void testEnumValues() {
		State[] states = State.values();
		assertEquals(6, states.length);
		assertEquals(State.Start, states[0]);
		assertEquals(State.Playing, states[1]);
		assertEquals(State.Checkmate, states[2]);
		assertEquals(State.Stalemate, states[3]);
		assertEquals(State.Resignation, states[4]);
		assertEquals(State.Draw, states[5]);
	}

	@Test
	void testEnumToString() {
		assertEquals("Start", State.Start.toString());
		assertEquals("Playing", State.Playing.toString());
		assertEquals("Checkmate", State.Checkmate.toString());
		assertEquals("Stalemate", State.Stalemate.toString());
		assertEquals("Resignation", State.Resignation.toString());
		assertEquals("Draw", State.Draw.toString());
	}

	@Test
	void testEnumValueOf() {
		assertEquals(State.Start, State.valueOf("Start"));
		assertEquals(State.Playing, State.valueOf("Playing"));
		assertEquals(State.Checkmate, State.valueOf("Checkmate"));
		assertEquals(State.Stalemate, State.valueOf("Stalemate"));
		assertEquals(State.Resignation, State.valueOf("Resignation"));
		assertEquals(State.Draw, State.valueOf("Draw"));
	}
}

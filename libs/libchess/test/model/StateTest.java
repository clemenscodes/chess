package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StateTest {

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
}

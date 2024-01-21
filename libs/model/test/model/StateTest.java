package model;

import static org.junit.jupiter.api.Assertions.*;

import api.model.State;
import org.junit.jupiter.api.Test;

public class StateTest {

	@Test
	void testEnumValues() {
		State[] states = State.values();
		assertEquals(8, states.length);
		assertEquals(State.Start, states[0]);
		assertEquals(State.Playing, states[1]);
		assertEquals(State.Checkmate, states[2]);
		assertEquals(State.Stalemate, states[3]);
		assertEquals(State.Resignation, states[4]);
		assertEquals(State.Draw, states[5]);
		assertEquals(State.DrawOffer, states[6]);
		assertEquals(State.Promotion, states[7]);
	}
}

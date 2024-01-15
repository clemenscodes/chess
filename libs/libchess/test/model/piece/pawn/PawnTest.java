package model.piece.pawn;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PawnTest {

	@Test
	void testPromotionMask() {
		String expected =
			"""
			11111111
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			11111111""";
		assertEquals(expected, Pawn.promotionMask.toString());
	}
}

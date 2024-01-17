package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class WriterTest {

	@Test
	void shouldApplyLoopBodyCorrectly() {
		IBitboard[] pieces = new IBitboard[Board.SIZE];
		LoopBodyMock loopBodyMock = new LoopBodyMock();
		StringBuilder result = Writer.loopOver(loopBodyMock, pieces);
		String expected =
			"""
			56 57 58 59 60 61 62 63
			48 49 50 51 52 53 54 55
			40 41 42 43 44 45 46 47
			32 33 34 35 36 37 38 39
			24 25 26 27 28 29 30 31
			16 17 18 19 20 21 22 23
			8 9 10 11 12 13 14 15
			0 1 2 3 4 5 6 7""";
		assertEquals(expected, result.toString());
	}

	private static class LoopBodyMock implements LoopBody {

		@Override
		public void apply(int rank, int file, StringBuilder stringBuilder, IBitboard[] pieces) {
			stringBuilder.append(Board.getSquareIndex(rank, file));
			if (file != 7) {
				stringBuilder.append(" ");
			}
		}
	}
}

package model.bits;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BitboardTest {

	@Test
	void shouldInitialize() {
		assertDoesNotThrow(() -> new Bitboard());
		assertDoesNotThrow(() -> new Bitboard(0xFFL));
	}

	@Test
	void shouldNegateBitboard() {
		IBitboard originalBoard = new Bitboard(0x123456789ABCDEF0L);
		IBitboard negatedBoard = Bitboard.negate(originalBoard);
		assertEquals(~originalBoard.getBits(), negatedBoard.getBits());
	}

	@Test
	void shouldMergeTwoBitboards() {
		IBitboard a = new Bitboard(0x00000000000000FFL);
		IBitboard b = new Bitboard(0xFF00000000000000L);
		IBitboard mergedBoard = Bitboard.merge(a, b);
		assertEquals(a.getBits() | b.getBits(), mergedBoard.getBits());
	}

	@Test
	void shouldIntersectTwoBitboards() {
		IBitboard a = new Bitboard(0x123456789ABCDEF0L);
		IBitboard b = new Bitboard(0xFEDCBA9876543210L);
		IBitboard intersectedBoard = Bitboard.intersect(a, b);
		assertEquals(a.getBits() & b.getBits(), intersectedBoard.getBits());
	}

	@Test
	void shouldCheckContainment() {
		IBitboard a = new Bitboard(0x123456789ABCDEF0L);
		IBitboard b = new Bitboard(0x0000000000000010L);
		assertTrue(Bitboard.overlap(a, b));
		assertTrue(Bitboard.overlap(b, a));
		assertFalse(Bitboard.overlap(b, new Bitboard()));
		assertFalse(Bitboard.overlap(a, new Bitboard()));
	}

	@Test
	void shouldCreateLeftShiftMask() {
		int bits = 5;
		IBitboard leftShiftMask = Bitboard.leftShiftMask(bits);
		assertEquals(1L << bits, leftShiftMask.getBits());
	}

	@Test
	void shouldCreateRightShiftMask() {
		IBitboard a = new Bitboard(0x0000000000000010L);
		int bits = 4;
		IBitboard rightShiftMask = Bitboard.rightShiftMask(a, bits);
		assertEquals(0x0000000000000001L, rightShiftMask.getBits());
	}

	@Test
	void shouldShiftBoard() {
		IBitboard a = new Bitboard(0x0000000000000010L);
		IBitboard shiftedBoard = Bitboard.shift(a, 2);
		IBitboard expected = new Bitboard(0x0000000000000040L);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldMergeManyBitboards() {
		IBitboard a = new Bitboard(0x00000000000000FFL);
		IBitboard b = new Bitboard(0x00FF000000000000L);
		IBitboard c = new Bitboard(0x0000000000FF0000L);
		IBitboard expected = new Bitboard(0x00FF000000FF00FFL);
		IBitboard mergedBoard = Bitboard.mergeMany(new IBitboard[] { a, b, c });
		assertEquals(expected.getBits(), mergedBoard.getBits());
	}

	@Test
	void shouldCheckShiftNorth() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftNorth(board);
		IBitboard expected = new Bitboard(0xFFFFFFFFFFFFFF00L);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftNorthEast() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftNorthEast(board);
		IBitboard expected = new Bitboard(0xFEFEFEFEFEFEFE00L);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftNorthWest() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftNorthWest(board);
		IBitboard expected = new Bitboard(0x7F7F7F7F7F7F7F00L);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftNorthNorthEast() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftNorthNorthEast(board);
		IBitboard expected = new Bitboard(0xFEFEFEFEFEFE0000L);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftNorthNorthWest() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftNorthNorthWest(board);
		IBitboard expected = new Bitboard(0x7F7F7F7F7F7F0000L);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftEast() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftEast(board);
		IBitboard expected = new Bitboard(0xFEFEFEFEFEFEFEFEL);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftEastEastNorth() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftEastEastNorth(board);
		IBitboard expected = new Bitboard(0xFCFCFCFCFCFCFC00L);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftEastEastSouth() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftEastEastSouth(board);
		IBitboard expected = new Bitboard(0xFCFCFCFCFCFCFCL);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftSouth() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftSouth(board);
		IBitboard expected = new Bitboard(0x00FFFFFFFFFFFFFFL);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftSouthEast() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftSouthEast(board);
		IBitboard expected = new Bitboard(0x00FEFEFEFEFEFEFEL);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftSouthWest() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftSouthWest(board);
		IBitboard expected = new Bitboard(0x007F7F7F7F7F7F7FL);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftSouthSouthEast() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftSouthSouthEast(board);
		IBitboard expected = new Bitboard(0x0000FEFEFEFEFEFEL);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftSouthSouthWest() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftSouthSouthWest(board);
		IBitboard expected = new Bitboard(0x00007F7F7F7F7F7FL);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftWest() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftWest(board);
		IBitboard expected = new Bitboard(0x7F7F7F7F7F7F7F7FL);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftWestWestNorth() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftWestWestNorth(board);
		IBitboard expected = new Bitboard(0x3F3F3F3F3F3F3F00L);
		System.out.println(board);
		System.out.println(shiftedBoard);
		System.out.println(Long.toHexString(shiftedBoard.getBits()));
		System.out.println(expected);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldShiftWestWestSouth() {
		IBitboard board = new Bitboard(-1);
		IBitboard shiftedBoard = Bitboard.shiftWestWestSouth(board);
		IBitboard expected = new Bitboard(0x003F3F3F3F3F3F3FL);
		assertEquals(expected.getBits(), shiftedBoard.getBits());
	}

	@Test
	void shouldCheckOverlap() {
		IBitboard a = new Bitboard(0x0000000000000010L);
		IBitboard b = new Bitboard(0x0000000000000030L);
		assertTrue(a.overlap(b));
		assertFalse(a.overlap(Bitboard.leftShiftMask(10)));
	}

	@Test
	void shouldMerge() {
		IBitboard a = new Bitboard(0x0000000000000010L);
		IBitboard b = new Bitboard(0x0000000000000020L);
		a.merge(b);
		assertEquals(0x0000000000000030L, a.getBits());
	}

	@Test
	void shouldIntersect() {
		IBitboard a = new Bitboard(0x0000000000000FFFL);
		IBitboard b = new Bitboard(0x0000000000000F00L);
		a.intersect(b);
		assertEquals(0x0000000000000F00L, a.getBits());
	}

	@Test
	void shouldLeftShift() {
		IBitboard a = new Bitboard(0x0000000000000001L);
		a.leftShift(3);
		assertEquals(0x0000000000000008L, a.getBits());
	}

	@Test
	void shouldRightShift() {
		IBitboard a = new Bitboard(0x0000000000000080L);
		a.rightShift(4);
		assertEquals(0x0000000000000008L, a.getBits());
	}

	@Test
	void shouldNegateBits() {
		IBitboard a = new Bitboard(0x00000000000000FFL);
		a.negateBits();
		assertEquals(0xFFFFFFFFFFFFFF00L, a.getBits());
	}

	@Test
	void shouldCopy() {
		IBitboard original = new Bitboard(0x00000000000000FFL);
		IBitboard copy = original.copy();
		assertEquals(original.getBits(), copy.getBits());
		assertNotSame(original, copy);
	}
}

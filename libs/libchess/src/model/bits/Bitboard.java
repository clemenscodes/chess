package model.bits;

import java.io.Serializable;
import model.board.Board;

public class Bitboard implements IBitboard, Serializable {

	public static final byte NORTH = Board.SIZE;
	public static final byte EAST = 1;
	public static final byte SOUTH = -Board.SIZE;
	public static final byte WEST = -1;
	public static final byte NORTH_EAST = NORTH + EAST;
	public static final byte SOUTH_EAST = SOUTH + EAST;
	public static final byte SOUTH_WEST = SOUTH + WEST;
	public static final byte NORTH_WEST = NORTH + WEST;
	public static final byte NORTH_NORTH_EAST = NORTH + NORTH_EAST;
	public static final byte NORTH_NORTH_WEST = NORTH + NORTH_WEST;
	public static final byte SOUTH_SOUTH_EAST = SOUTH + SOUTH_EAST;
	public static final byte SOUTH_SOUTH_WEST = SOUTH + SOUTH_WEST;
	public static final byte EAST_EAST_NORTH = EAST + NORTH_EAST;
	public static final byte EAST_EAST_SOUTH = EAST + SOUTH_EAST;
	public static final byte WEST_WEST_NORTH = WEST + NORTH_WEST;
	public static final byte WEST_WEST_SOUTH = WEST + SOUTH_WEST;
	public static final IBitboard firstFile = new Bitboard(0x0101010101010101L);
	public static final IBitboard secondFile = new Bitboard(0x0202020202020202L);
	public static final IBitboard notFirstFile = Bitboard.negate(firstFile);
	public static final IBitboard lastFile = new Bitboard(0x8080808080808080L);
	public static final IBitboard notLastFile = Bitboard.negate(lastFile);
	public static final IBitboard secondLastFile = new Bitboard(0x4040404040404040L);
	public static final IBitboard firstRank = new Bitboard(0x00000000000000FFL);
	public static final IBitboard notFirstRank = Bitboard.negate(firstRank);
	public static final IBitboard lastRank = new Bitboard(0xFF00000000000000L);
	public static final IBitboard notLastRank = Bitboard.negate(lastRank);
	public static final IBitboard diagonal = new Bitboard(0x8040201008040201L);
	public static final IBitboard antiDiagonal = new Bitboard(0x0102040810204080L);
	public static final IBitboard lightSquares = new Bitboard(0x55AA55AA55AA55AAL);
	public static final IBitboard darkSquares = Bitboard.negate(lightSquares);
	private static final IBitboard[] singleBits = new IBitboard[Board.SIZE * Board.SIZE];

	static {
		for (int rank = 7; rank >= 0; rank--) {
			for (int file = 0; file < Board.SIZE; file++) {
				int index = Board.getSquareIndex(rank, file);
				singleBits[index] = Bitboard.leftShiftMask(index);
			}
		}
	}

	public static IBitboard getSingleBit(int index) {
		if (index < 0 || index >= singleBits.length) {
			throw new Error("Invalid index " + index);
		}
		return singleBits[index];
	}

	public static boolean checkBit(IBitboard board, int index) {
		return Bitboard.intersect(board, Bitboard.getSingleBit(index)).getBits() != 0;
	}

	public static IBitboard setBit(IBitboard board, int index) {
		return Bitboard.merge(board, Bitboard.getSingleBit(index));
	}

	public static IBitboard toggleBit(IBitboard board, int index) {
		return Bitboard.toggle(board, Bitboard.getSingleBit(index));
	}

	public static IBitboard toggle(IBitboard a, IBitboard b) {
		return new Bitboard(a.getBits() ^ b.getBits());
	}

	public static IBitboard unsetBit(IBitboard board, int index) {
		return Bitboard.toggle(Bitboard.setBit(board, index), Bitboard.getSingleBit(index));
	}

	public static IBitboard negate(IBitboard board) {
		return new Bitboard(~board.getBits());
	}

	public static IBitboard mergeMany(IBitboard[] bitboards) {
		long mergedBits = 0L;
		for (var board : bitboards) {
			mergedBits |= board.getBits();
		}
		return new Bitboard(mergedBits);
	}

	public static IBitboard merge(IBitboard a, IBitboard b) {
		return new Bitboard(a.getBits() | b.getBits());
	}

	public static IBitboard intersect(IBitboard a, IBitboard b) {
		return new Bitboard(a.getBits() & b.getBits());
	}

	public static boolean overlap(IBitboard a, IBitboard b) {
		return Bitboard.intersect(a, b).getBits() != 0;
	}

	public static IBitboard rightShiftMask(IBitboard board, int bits) {
		return new Bitboard(board.getBits() >>> bits);
	}

	public static IBitboard shift(IBitboard board, int index) {
		long bits = board.getBits();
		return new Bitboard(index < 0 ? bits >>> -index : bits << index);
	}

	public static IBitboard shiftNorth(IBitboard board) {
		return Bitboard.shift(board, NORTH);
	}

	public static IBitboard shiftNorthEast(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, NORTH_EAST), notFirstFile);
	}

	public static IBitboard shiftNorthWest(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, NORTH_WEST), notLastFile);
	}

	public static IBitboard shiftNorthNorthEast(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, NORTH_NORTH_EAST), notFirstFile);
	}

	public static IBitboard shiftNorthNorthWest(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, NORTH_NORTH_WEST), notLastFile);
	}

	public static IBitboard shiftEast(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, EAST), notFirstFile);
	}

	public static IBitboard shiftEastEastNorth(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, EAST_EAST_NORTH), Bitboard.negate(Bitboard.merge(firstFile, secondFile)));
	}

	public static IBitboard shiftEastEastSouth(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, EAST_EAST_SOUTH), Bitboard.negate(Bitboard.merge(firstFile, secondFile)));
	}

	public static IBitboard shiftSouth(IBitboard board) {
		return Bitboard.shift(board, SOUTH);
	}

	public static IBitboard shiftSouthEast(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, SOUTH_EAST), notFirstFile);
	}

	public static IBitboard shiftSouthWest(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, SOUTH_WEST), notLastFile);
	}

	public static IBitboard shiftSouthSouthEast(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, SOUTH_SOUTH_EAST), notFirstFile);
	}

	public static IBitboard shiftSouthSouthWest(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, SOUTH_SOUTH_WEST), notLastFile);
	}

	public static IBitboard shiftWest(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, WEST), notLastFile);
	}

	public static IBitboard shiftWestWestNorth(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, WEST_WEST_NORTH), Bitboard.negate(Bitboard.merge(lastFile, secondLastFile)));
	}

	public static IBitboard shiftWestWestSouth(IBitboard board) {
		return Bitboard.intersect(Bitboard.shift(board, WEST_WEST_SOUTH), Bitboard.negate(Bitboard.merge(lastFile, secondLastFile)));
	}

	private static IBitboard leftShiftMask(int bits) {
		return new Bitboard(1L << bits);
	}

	private long bits;

	public Bitboard(long bits) {
		setBits(bits);
	}

	public Bitboard() {
		setBits(0);
	}

	public long getBits() {
		return bits;
	}

	public void setBits(long bits) {
		this.bits = bits;
	}

	public boolean overlap(IBitboard board) {
		return Bitboard.overlap(this, board);
	}

	public void merge(IBitboard board) {
		setBits(Bitboard.merge(this, board).getBits());
	}

	public void intersect(IBitboard board) {
		setBits(Bitboard.intersect(this, board).getBits());
	}

	public void leftShift(int bits) {
		setBits(getBits() << bits);
	}

	public void rightShift(int bits) {
		setBits(getBits() >>> bits);
	}

	public void negateBits() {
		setBits(Bitboard.negate(this).getBits());
	}

	public IBitboard copy() {
		return new Bitboard(getBits());
	}

	@Override
	public String toString() {
		var stringBuilder = new StringBuilder();
		for (int rank = 7; rank >= 0; rank--) {
			for (int file = 0; file < Board.SIZE; file++) {
				int index = Board.getSquareIndex(rank, file);
				IBitboard mask = Bitboard.getSingleBit(index);
				IBitboard intersection = Bitboard.intersect(this, mask);
				long bit = Bitboard.rightShiftMask(intersection, index).getBits();
				stringBuilder.append(bit == -1 ? 1 : bit);
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
}

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
	public static final IBitboard lastFile = new Bitboard(0x8080808080808080L);
	public static final IBitboard firstRank = new Bitboard(0x00000000000000FFL);
	public static final IBitboard lastRank = new Bitboard(0xFF00000000000000L);
	public static final IBitboard diagonal = new Bitboard(0x8040201008040201L);
	public static final IBitboard antiDiagonal = new Bitboard(0x0102040810204080L);
	public static final IBitboard lightSquares = new Bitboard(0x55AA55AA55AA55AAL);
	public static final IBitboard darkSquares = new Bitboard(0xAA55AA55AA55AA55L);

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

	public static boolean contains(IBitboard a, IBitboard b) {
		return (a.getBits() & b.getBits()) != 0;
	}

	public static IBitboard leftShiftMask(int bits) {
		return new Bitboard(1L << bits);
	}

	public static boolean isSubset(IBitboard a, IBitboard b) {
		return (a.getBits() & b.getBits()) == a.getBits();
	}

	public static IBitboard getSuperset(IBitboard a, IBitboard b) {
		return new Bitboard(a.getBits() | b.getBits());
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

	public boolean contains(IBitboard board) {
		return Bitboard.contains(this, board);
	}

	public void merge(IBitboard board) {
		setBits(Bitboard.merge(this, board).getBits());
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

	@Override
	public String toString() {
		var stringBuilder = new StringBuilder();
		for (int rank = 7; rank >= 0; rank--) {
			for (int file = 0; file < Board.SIZE; file++) {
				int index = Board.getSquareIndex(rank, file);
				long mask = 1L << index;
				long bit = (getBits() & mask) >> index;
				stringBuilder.append(bit == -1 ? 1 : bit);
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
}

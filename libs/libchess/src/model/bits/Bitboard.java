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
	public static final Bitboard firstFile = new Bitboard(0x0101010101010101L);
	public static final Bitboard lastFile = new Bitboard(0x8080808080808080L);
	public static final Bitboard firstRank = new Bitboard(0x00000000000000FFL);
	public static final Bitboard lastRank = new Bitboard(0xFF00000000000000L);
	public static final Bitboard diagonal = new Bitboard(0x8040201008040201L);
	public static final Bitboard antiDiagonal = new Bitboard(0x0102040810204080L);
	public static final Bitboard lightSquares = new Bitboard(0x55AA55AA55AA55AAL);
	public static final Bitboard darkSquares = new Bitboard(0xAA55AA55AA55AA55L);

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

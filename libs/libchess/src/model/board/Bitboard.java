package model.board;

import java.io.Serializable;

public class Bitboard implements Serializable {

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
				int index = rank * Board.SIZE + file;
				long mask = 1L << index;
				long bit = (getBits() & mask) >> index;
				stringBuilder.append(bit == -1 ? 1 : bit);
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
}

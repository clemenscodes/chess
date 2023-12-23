package model.piece;

import java.io.Serializable;
import model.board.Bitboard;

public abstract class Piece implements Movable, Serializable {

	private Pieces variant;
	private Bitboard bitboard;

	public Piece(Pieces variant) {
		setVariant(variant);
		setBitboard(new Bitboard());
	}

	public Pieces getVariant() {
		return variant;
	}

	public Bitboard getBitboard() {
		return bitboard;
	}

	public void setBitboard(Bitboard bitboard) {
		this.bitboard = bitboard;
	}

	private void setVariant(Pieces variant) {
		this.variant = variant;
	}
}

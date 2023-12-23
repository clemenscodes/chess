package model.piece;

import java.io.Serializable;

public abstract class Piece implements Movable, Serializable {

	private Pieces variant;
	private long bits;

	public Piece(Pieces variant) {
		setVariant(variant);
	}

	public void setBits(long bits) {
		this.bits = bits;
	}

	public long getBits() {
		return bits;
	}

	public Pieces getVariant() {
		return variant;
	}

	private void setVariant(Pieces variant) {
		this.variant = variant;
	}
}

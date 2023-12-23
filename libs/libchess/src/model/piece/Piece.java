package model.piece;

import java.io.Serializable;

public abstract class Piece implements Movable, Serializable {

	private Pieces variant;
	private long bits;

	public Piece(Pieces variant) {
		setVariant(variant);
	}

	public Pieces getVariant() {
		return variant;
	}

	public long getBits() {
		return bits;
	}

	public void setBits(long bits) {
		this.bits = bits;
	}

	private void setVariant(Pieces variant) {
		this.variant = variant;
	}
}

package model.piece;

import java.io.Serializable;

public abstract class Piece implements Movable, Serializable {

	private Pieces variant;
	private char symbol;
	private long bits;

	public Piece(Pieces variant, char symbol) {
		setVariant(variant);
		setSymbol(symbol);
	}

	protected void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	protected void setBits(long bits) {
		this.bits = bits;
	}

	public long getBits() {
		return bits;
	}

	public char getSymbol() {
		return symbol;
	}

	public Pieces getVariant() {
		return variant;
	}

	private void setVariant(Pieces variant) {
		this.variant = variant;
	}
}

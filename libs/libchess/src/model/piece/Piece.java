package model.piece;

import java.io.Serializable;

public abstract class Piece implements IPiece, Serializable {

	private Pieces variant;
	private char symbol;

	public Piece(Pieces variant, char symbol) {
		setVariant(variant);
		setSymbol(symbol);
	}

	protected void setSymbol(char symbol) {
		this.symbol = symbol;
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

	public abstract boolean isValidMove(int position, Piece[] pieces);
}

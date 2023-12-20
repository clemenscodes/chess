package model.piece;

import java.io.Serializable;

public abstract class Piece implements IPiece, Serializable {

	private boolean isWhite;
	private Pieces variant;
	private int material;
	private int position;
	private char symbol;

	public Piece(boolean isWhite, Pieces variant, int material, int position) {
		setIsWhite(isWhite);
		setVariant(variant);
		setMaterial(material);
		setPosition(position);
	}

	public abstract boolean isValidMove(int position, Piece[] pieces);

	protected void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public Pieces getVariant() {
		return variant;
	}

	public int getMaterial() {
		return material;
	}

	public int getPosition() {
		return position;
	}

	private void setIsWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}

	private void setVariant(Pieces variant) {
		this.variant = variant;
	}

	private void setMaterial(int material) {
		this.material = material;
	}

	protected void setPosition(int position) {
		this.position = position;
	}
}

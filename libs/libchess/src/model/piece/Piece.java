package model.piece;

import java.io.Serializable;
import model.enums.Color;

public abstract class Piece implements IPiece, Serializable {

	private Color color;
	private Pieces variant;
	private int material;
	private int id;
	private int position;
	private char symbol;

	public Piece(
		Color color,
		Pieces variant,
		int material,
		int position,
		int id
	) {
		setColor(color);
		setVariant(variant);
		setMaterial(material);
		setPosition(position);
		setId(id);
	}

	public Piece(Pieces variant, int position, int id) {
		setVariant(variant);
		setPosition(position);
		setId(id);
	}

	public abstract boolean isValidMove(int position);

	public void move(int position) {
		if (!isValidMove(position)) {
			throw new Error("Invalid move");
		}
		setPosition(position);
	}

	protected void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}

	public Color getColor() {
		return color;
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

	public int getId() {
		return id;
	}

	private void setColor(Color color) {
		this.color = color;
	}

	private void setVariant(Pieces variant) {
		this.variant = variant;
	}

	private void setMaterial(int material) {
		this.material = material;
	}

	private void setId(int id) {
		this.id = id;
	}

	protected void setPosition(int position) {
		this.position = position;
	}
}

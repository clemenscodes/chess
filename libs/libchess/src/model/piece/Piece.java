package model.piece;

import java.io.Serializable;
import model.Color;

public class Piece implements Serializable {

	private Color color;
	private String name;
	private int material;
	private int position;
	private int id;
	private char symbol;

	public Piece(
		Color color,
		String name,
		int material,
		int position,
		int id,
		char symbol
	) {
		setColor(color);
		setName(name);
		setMaterial(material);
		setPosition(position);
		setId(id);
		setSymbol(symbol);
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
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

	public char getSymbol() {
		return symbol;
	}

	private void setColor(Color color) {
		this.color = color;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setPosition(int position) {
		this.position = position;
	}

	private void setMaterial(int material) {
		this.material = material;
	}

	private void setId(int id) {
		this.id = id;
	}

	private void setSymbol(char symbol) {
		this.symbol = symbol;
	}
}

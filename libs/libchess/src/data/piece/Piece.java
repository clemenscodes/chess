package data.piece;

import data.Color;
import java.io.Serializable;

public class Piece implements Serializable {

	private Color color;
	private String name;
	private int material;
	private int position;
	private int id;

	public Piece(Color color, String name, int material, int position, int id) {
		setColor(color);
		setName(name);
		setMaterial(material);
		setPosition(position);
		setId(id);
	}

	private void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	private Color getColor() {
		return color;
	}

	private void setColor(Color color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public int getMaterial() {
		return material;
	}

	public int getPosition() {
		return position;
	}

	private void setPosition(int position) {
		this.position = position;
	}

	private void setMaterial(int material) {
		this.material = material;
	}
}

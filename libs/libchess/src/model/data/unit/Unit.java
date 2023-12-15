package model.data.unit;

import java.io.Serializable;
import model.data.Color;

public class Unit implements Serializable {

	Color color;
	String name;
	int material;
	int position;

	public Unit(Color color, String name, int material) {
		setColor(color);
		setName(name);
		setMaterial(material);
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

package model.data.unit;

import model.data.Color;
import java.io.Serializable;

public class Unit implements Serializable {

    Color color;
	String name;
	int material;
	int position;

	public Unit(Color color, String name, int material) {
		setName(name);
		setMaterial(material);
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

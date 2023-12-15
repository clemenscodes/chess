package model.data.player;

import java.io.Serializable;
import model.data.Color;
import model.data.unit.Unit;

public class Player implements Serializable {

	private Unit[] units;
	Color color;

	public Player(Color color, Unit[] units) {
		setColor(color);
		setUnits(units);
	}

	private void setUnits(Unit[] units) {
		this.units = units;
	}

	public Color getColor() {
		return color;
	}

	public Unit[] getUnits() {
		return units;
	}

	private void setColor(Color color) {
		this.color = color;
	}
}

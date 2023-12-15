package model.data.board;

import java.io.Serializable;
import model.data.unit.Unit;

public class Field implements Serializable {

	private Unit unit;

	public Field(Unit unit) {
		setUnit(unit);
	}

	public Unit getUnit() {
		return unit;
	}

	private void setUnit(Unit unit) {
		this.unit = unit;
	}
}

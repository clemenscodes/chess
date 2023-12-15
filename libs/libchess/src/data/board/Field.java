package data.board;

import data.piece.Unit;
import java.io.Serializable;

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

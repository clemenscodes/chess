package model.data.player;

import java.io.Serializable;
import model.data.Color;
import model.data.unit.Unit;

public class White extends Player implements Serializable {

	public White(Unit[] units) {
		super(Color.WHITE, units);
	}
}

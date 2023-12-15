package model.data.player;

import java.io.Serializable;
import model.data.Color;
import model.data.unit.Unit;

public class Black extends Player implements Serializable {

	public Black(Unit[] units) {
		super(Color.BLACK, units);
	}
}

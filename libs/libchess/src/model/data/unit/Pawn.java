package model.data.unit;

import java.io.Serializable;
import model.data.Color;

public class Pawn extends Unit implements Serializable {

	public Pawn(Color color) {
		super(color, "pawn", 1);
	}
}

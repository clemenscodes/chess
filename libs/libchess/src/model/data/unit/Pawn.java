package model.data.unit;

import java.io.Serializable;
import model.data.Color;

public class Pawn extends Unit implements Serializable {

	public Pawn(Color color, int position, int id) {
		super(color, "pawn", 1, position, id);
	}
}

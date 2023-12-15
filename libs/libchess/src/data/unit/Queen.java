package data.unit;

import data.Color;
import java.io.Serializable;

public class Queen extends Unit implements Serializable {

	public Queen(Color color, int position, int id) {
		super(color, "queen", 9, position, id);
	}
}

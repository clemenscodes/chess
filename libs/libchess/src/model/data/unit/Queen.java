package model.data.unit;

import java.io.Serializable;
import model.data.Color;

public class Queen extends Unit implements Serializable {

	public Queen(Color color, int position, int id) {
		super(color, "queen", 9, position, id);
	}
}

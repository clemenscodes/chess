package data.unit;

import data.Color;
import java.io.Serializable;

public class Rook extends Unit implements Serializable {

	public Rook(Color color, int position, int id) {
		super(color, "rook", 5, position, id);
	}
}

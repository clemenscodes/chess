package model.data.unit;

import java.io.Serializable;
import model.data.Color;

public class Rook extends Unit implements Serializable {

	public Rook(Color color, int position, int id) {
		super(color, "rook", 5, position, id);
	}
}

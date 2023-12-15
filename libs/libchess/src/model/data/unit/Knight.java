package model.data.unit;

import java.io.Serializable;
import model.data.Color;

public class Knight extends Unit implements Serializable {

	public Knight(Color color, int position, int id) {
		super(color, "knight", 3, position, id);
	}
}

package data.unit;

import data.Color;
import java.io.Serializable;

public class King extends Unit implements Serializable {

	public King(Color color, int position, int id) {
		super(color, "king", 0, position, id);
	}
}

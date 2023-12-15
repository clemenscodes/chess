package model.data.unit;

import java.io.Serializable;
import model.data.Color;

public class Knight extends Unit implements Serializable {

	public Knight(Color color) {
		super(color, "knight", 3);
	}
}

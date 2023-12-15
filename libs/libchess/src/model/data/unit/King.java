package model.data.unit;

import java.io.Serializable;
import model.data.Color;

public class King extends Unit implements Serializable {

	public King(Color color) {
		super(color, "king", 0);
	}
}

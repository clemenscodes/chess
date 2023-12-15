package model.data.unit;

import java.io.Serializable;
import model.data.Color;

public class Bishop extends Unit implements Serializable {

	public Bishop(Color color) {
		super(color, "bishop", 3);
	}
}

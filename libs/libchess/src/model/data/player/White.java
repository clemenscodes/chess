package model.data.player;

import java.io.Serializable;
import model.data.Color;

public class White extends Player implements Serializable {

	public White() {
		super(Color.WHITE);
	}
}

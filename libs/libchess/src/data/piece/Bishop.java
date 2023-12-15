package data.piece;

import data.Color;
import java.io.Serializable;

public class Bishop extends Piece implements Serializable {

	public Bishop(Color color, int position, int id) {
		super(color, "bishop", 3, position, id);
	}
}

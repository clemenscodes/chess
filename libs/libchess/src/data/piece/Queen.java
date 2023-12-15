package data.piece;

import data.Color;
import java.io.Serializable;

public class Queen extends Piece implements Serializable {

	public Queen(Color color, int position, int id) {
		super(color, "queen", 9, position, id);
	}
}

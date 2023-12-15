package data.piece;

import data.Color;
import java.io.Serializable;

public class Knight extends Piece implements Serializable {

	public Knight(Color color, int position, int id) {
		super(color, "knight", 3, position, id);
	}
}

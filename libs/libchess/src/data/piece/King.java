package data.piece;

import data.Color;
import java.io.Serializable;

public class King extends Piece implements Serializable {

	public King(Color color, int position, int id) {
		super(color, "king", 0, position, id);
	}
}

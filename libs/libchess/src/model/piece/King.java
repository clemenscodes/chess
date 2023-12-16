package model.piece;

import java.io.Serializable;
import model.Color;

public class King extends Piece implements Serializable {

	public King(Color color, int position, int id) {
		super(color, "king", 0, position, id, 'k');
	}
}

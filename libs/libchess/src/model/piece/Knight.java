package model.piece;

import java.io.Serializable;
import model.Color;

public class Knight extends Piece implements Serializable {

	public Knight(Color color, int position, int id) {
		super(color, "knight", 3, position, id);
	}
}

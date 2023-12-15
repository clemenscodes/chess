package model.piece;

import java.io.Serializable;
import model.Color;

public class Rook extends Piece implements Serializable {

	public Rook(Color color, int position, int id) {
		super(color, "rook", 5, position, id);
	}
}

package model.piece;

import java.io.Serializable;
import model.Color;

public class Pawn extends Piece implements Serializable {

	public Pawn(Color color, int position, int id) {
		super(color, "pawn", 1, position, id);
	}
}

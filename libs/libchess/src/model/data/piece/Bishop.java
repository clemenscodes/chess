package model.data.piece;

import java.io.Serializable;
import model.data.Color;

public class Bishop extends Piece implements Serializable {

	public Bishop(Color color, int position, int id) {
		super(color, "bishop", 3, position, id);
	}
}

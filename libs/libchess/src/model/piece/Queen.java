package model.piece;

import java.io.Serializable;
import model.Color;

public class Queen extends Piece implements Serializable {

	public Queen(Color color, int position, int id) {
		super(color, "queen", 9, position, id, 'q');
	}
}

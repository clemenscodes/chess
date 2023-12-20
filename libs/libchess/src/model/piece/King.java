package model.piece;

import java.io.Serializable;
import model.Color;

public class King extends Piece implements Serializable {

	public King(Color color, int position, int id) {
		super(color, Pieces.King, 0, position, id);
		setSymbol(getColor() == Color.White ? '♔' : '♚');
	}

	@Override
	public boolean isValidMove(int position) {
		return false;
	}
}

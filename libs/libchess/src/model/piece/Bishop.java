package model.piece;

import java.io.Serializable;
import model.Color;

public class Bishop extends Piece implements Serializable {

	public Bishop(Color color, int position, int id) {
		super(color, Pieces.Bishop, 3, position, id);
		setSymbol(getColor() == Color.White ? '♗' : '♝');
	}

	@Override
	public boolean isValidMove(int position) {
		return false;
	}
}

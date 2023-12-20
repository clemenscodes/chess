package model.piece;

import java.io.Serializable;
import model.Color;

public class Knight extends Piece implements Serializable {

	public Knight(Color color, int position, int id) {
		super(color, Pieces.Knight, 3, position, id);
		setSymbol(getColor() == Color.White ? '♘' : '♞');
	}

	@Override
	public boolean isValidMove(int position) {
		return false;
	}
}

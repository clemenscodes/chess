package model.piece;

import java.io.Serializable;
import model.Color;

public class Rook extends Piece implements Serializable {

	public Rook(Color color, int position, int id) {
		super(color, Pieces.Rook, 5, position, id);
		setSymbol(getColor() == Color.White ? '♖' : '♜');
	}

	@Override
	public boolean isValidMove(int position) {
		return false;
	}
}

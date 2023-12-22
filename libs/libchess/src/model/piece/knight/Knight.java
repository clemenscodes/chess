package model.piece.knight;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Knight extends Piece implements Serializable {

	public Knight(Pieces variant, char symbol) {
		super(variant, symbol);
	}

	@Override
	public boolean isValidMove(int position, Piece[] pieces) {
		return false;
	}
}

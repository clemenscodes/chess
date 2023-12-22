package model.piece.king;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class King extends Piece implements Serializable {

	public King(Pieces variant, char symbol) {
		super(variant, symbol);
	}

	@Override
	public boolean isValidMove(int position, Piece[] pieces) {
		return false;
	}
}

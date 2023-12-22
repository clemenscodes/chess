package model.piece.bishop;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Bishop extends Piece implements Serializable {

	public Bishop(Pieces variant, char symbol) {
		super(variant, symbol);
	}

	@Override
	public boolean isValidMove(int position, Piece[] pieces) {
		return false;
	}
}

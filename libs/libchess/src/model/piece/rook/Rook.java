package model.piece.rook;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Rook extends Piece implements Serializable {

	public Rook(Pieces variant, char symbol) {
		super(variant, symbol);
	}

	@Override
	public boolean isValidMove(int position, Piece[] pieces) {
		return false;
	}
}

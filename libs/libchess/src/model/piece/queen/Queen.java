package model.piece.queen;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Queen extends Piece implements Serializable {

	public Queen(Pieces variant, char symbol) {
		super(variant, symbol);
	}

	@Override
	public boolean isValidMove(int position, Piece[] pieces) {
		return false;
	}
}

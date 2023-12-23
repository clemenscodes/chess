package model.piece.pawn;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Pawn extends Piece implements Serializable {

	public Pawn(Pieces variant) {
		super(variant);
	}
}

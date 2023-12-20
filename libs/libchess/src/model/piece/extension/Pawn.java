package model.piece.extension;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;

public class Pawn extends Piece implements Serializable {

	public Pawn(boolean isWhite, int position) {
		super(isWhite, Pieces.Pawn, 1, position);
		setSymbol(isWhite() ? '♙' : '♟');
	}

	@Override
	public boolean isValidMove(int position, Piece[] pieces) {
		return false;
	}
}

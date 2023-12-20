package model.piece.extension;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;

public class Bishop extends Piece implements Serializable {

	public Bishop(boolean isWhite, int position) {
		super(isWhite, Pieces.Bishop, 3, position);
		setSymbol(isWhite() ? '♗' : '♝');
	}

	@Override
	public boolean isValidMove(int position, Piece[] pieces) {
		return false;
	}
}

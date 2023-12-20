package model.piece.extension;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;

public class King extends Piece implements Serializable {

	public King(boolean isWhite, int position) {
		super(isWhite, Pieces.King, 0, position);
		setSymbol(isWhite() ? '♔' : '♚');
	}

	@Override
	public boolean isValidMove(int position, Piece[] pieces) {
		return false;
	}
}

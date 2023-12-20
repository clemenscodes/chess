package model.piece.extension;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;

public class Rook extends Piece implements Serializable {

	public Rook(boolean isWhite, int position) {
		super(isWhite, Pieces.Rook, 5, position);
		setSymbol(isWhite() ? '♖' : '♜');
	}

	@Override
	public boolean isValidMove(int position, Piece[] pieces) {
		return false;
	}
}

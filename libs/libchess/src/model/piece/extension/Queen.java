package model.piece.extension;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;

public class Queen extends Piece implements Serializable {

	public Queen(boolean isWhite, int position) {
		super(isWhite, Pieces.Queen, 9, position);
		setSymbol(isWhite() ? '♕' : '♛');
	}

	@Override
	public boolean isValidMove(int position, Piece[] pieces) {
		return false;
	}
}

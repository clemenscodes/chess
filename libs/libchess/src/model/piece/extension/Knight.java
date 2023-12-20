package model.piece.extension;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;

public class Knight extends Piece implements Serializable {

	public Knight(boolean isWhite, int position) {
		super(isWhite, Pieces.Knight, 3, position);
		setSymbol(isWhite() ? '♘' : '♞');
	}

	@Override
	public boolean isValidMove(int position, Piece[] pieces) {
		return false;
	}
}

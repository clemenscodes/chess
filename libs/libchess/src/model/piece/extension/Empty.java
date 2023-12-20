package model.piece.extension;

import java.io.Serializable;
import model.piece.IPiece;
import model.piece.Piece;
import model.piece.Pieces;

public class Empty extends Piece implements Serializable, IPiece {

	public Empty(int position, int id) {
		super(Pieces.Empty, position, id);
		setSymbol(' ');
	}

	@Override
	public boolean isValidMove(int position) {
		return false;
	}
}

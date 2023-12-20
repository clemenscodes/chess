package model.piece;

import java.io.Serializable;
import model.Color;

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

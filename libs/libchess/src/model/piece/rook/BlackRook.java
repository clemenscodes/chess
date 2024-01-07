package model.piece.rook;

import java.io.Serializable;
import model.piece.Pieces;

public class BlackRook extends Rook implements Serializable {

	public static final char SYMBOL = '♜';

	public BlackRook() {
		super(Pieces.BlackRook);
	}
}

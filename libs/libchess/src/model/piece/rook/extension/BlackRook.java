package model.piece.rook.extension;

import java.io.Serializable;
import model.piece.Pieces;
import model.piece.rook.Rook;

public class BlackRook extends Rook implements Serializable {

	public static final char SYMBOL = '♜';

	public BlackRook() {
		super(Pieces.BlackRook);
	}
}

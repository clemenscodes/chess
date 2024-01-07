package model.piece.rook;

import java.io.Serializable;
import model.piece.Pieces;

public class WhiteRook extends Rook implements Serializable {

	public static final char SYMBOL = '♖';

	public WhiteRook() {
		super(Pieces.WhiteRook);
	}
}

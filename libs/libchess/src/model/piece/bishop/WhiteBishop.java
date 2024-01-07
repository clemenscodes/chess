package model.piece.bishop;

import java.io.Serializable;
import model.piece.Pieces;

public class WhiteBishop extends Bishop implements Serializable {

	public static final char SYMBOL = '♗';

	public WhiteBishop() {
		super(Pieces.WhiteBishop);
	}
}

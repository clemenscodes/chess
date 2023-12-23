package model.piece.bishop.extension;

import java.io.Serializable;
import model.piece.Pieces;
import model.piece.bishop.Bishop;

public class WhiteBishop extends Bishop implements Serializable {

	public static final char SYMBOL = '♗';

	public WhiteBishop() {
		super(Pieces.WhiteBishop);
	}
}

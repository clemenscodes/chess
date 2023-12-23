package model.piece.rook.extension;

import java.io.Serializable;
import model.piece.Pieces;
import model.piece.rook.Rook;

public class WhiteRook extends Rook implements Serializable {

	public static final char SYMBOL = '♖';

	public WhiteRook() {
		super(Pieces.WhiteRook);
	}
}

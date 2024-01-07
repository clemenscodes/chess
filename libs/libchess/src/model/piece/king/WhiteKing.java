package model.piece.king;

import java.io.Serializable;
import model.piece.Pieces;

public class WhiteKing extends King implements Serializable {

	public static final char SYMBOL = '♔';

	public WhiteKing() {
		super(Pieces.WhiteKing);
	}
}

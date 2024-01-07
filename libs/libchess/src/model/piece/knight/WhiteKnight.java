package model.piece.knight;

import java.io.Serializable;
import model.piece.Pieces;

public class WhiteKnight extends Knight implements Serializable {

	public static final char SYMBOL = '♘';

	public WhiteKnight() {
		super(Pieces.WhiteKnight);
	}
}

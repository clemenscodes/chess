package model.piece.knight;

import java.io.Serializable;
import model.piece.Pieces;

public class BlackKnight extends Knight implements Serializable {

	public static final char SYMBOL = '♞';

	public BlackKnight() {
		super(Pieces.BlackKnight);
	}
}

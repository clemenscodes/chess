package model.piece.king;

import java.io.Serializable;
import model.piece.Pieces;

public class BlackKing extends King implements Serializable {

	public static final char SYMBOL = '♚';

	public BlackKing() {
		super(Pieces.BlackKing);
	}
}

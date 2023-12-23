package model.piece.king.extension;

import java.io.Serializable;
import model.piece.Pieces;
import model.piece.king.King;

public class BlackKing extends King implements Serializable {

	public static final char SYMBOL = '♚';

	public BlackKing() {
		super(Pieces.BlackKing);
	}
}

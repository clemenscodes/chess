package model.piece.king.extension;

import java.io.Serializable;
import model.piece.Pieces;
import model.piece.king.King;

public class WhiteKing extends King implements Serializable {

	public WhiteKing() {
		super(Pieces.WhiteKing, '♔');
	}
}

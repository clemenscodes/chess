package model.piece.knight;

import model.bits.IBitboard;
import model.piece.Pieces;

public class WhiteKnight extends Knight {

	public WhiteKnight() {
		super(Pieces.WhiteKnight);
	}

	public WhiteKnight(IBitboard board) {
		super(Pieces.WhiteKnight, board);
	}
}

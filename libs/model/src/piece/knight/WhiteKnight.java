package piece.knight;

import api.model.bits.IBitboard;
import api.model.piece.Pieces;

public class WhiteKnight extends Knight {

	public WhiteKnight() {
		super(Pieces.WhiteKnight);
	}

	public WhiteKnight(IBitboard board) {
		super(Pieces.WhiteKnight, board);
	}
}

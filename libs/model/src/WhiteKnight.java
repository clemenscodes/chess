import api.model.bits.IBitboard;
import api.model.piece.Pieces;

class WhiteKnight extends Knight {

	WhiteKnight() {
		super(Pieces.WhiteKnight);
	}

	WhiteKnight(IBitboard board) {
		super(Pieces.WhiteKnight, board);
	}
}

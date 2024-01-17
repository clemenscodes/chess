package model;

import api.model.IBitboard;
import api.model.Pieces;

class WhiteKnight extends Knight {

	WhiteKnight() {
		super(Pieces.WhiteKnight);
	}

	WhiteKnight(IBitboard board) {
		super(Pieces.WhiteKnight, board);
	}
}

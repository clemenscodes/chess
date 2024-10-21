package model;

import api.Pieces;

class WhiteKnight extends Knight {

    WhiteKnight() {
        super(Pieces.WhiteKnight);
    }

    WhiteKnight(IBitboard board) {
        super(Pieces.WhiteKnight, board);
    }
}

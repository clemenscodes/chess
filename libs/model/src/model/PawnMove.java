package model;

import api.Square;

abstract class PawnMove extends IrreversibleMove {

	PawnMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		board.getPiece(source).getBitboard().unsetBitByIndex(Board.getIndex(source));
	}
}

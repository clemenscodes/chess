package model;

import api.Square;

abstract class IrreversibleMove extends Move {

	IrreversibleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		IPiece piece = board.getPiece(source);
		if (piece instanceof King) {
			board.getFen().incrementHalfMoveClock();
		} else {
			board.getFen().resetHalfMoveClock();
		}
	}
}

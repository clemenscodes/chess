package model;

import api.model.IBoard;
import api.model.Square;

abstract class ReversibleMove extends Move {

	ReversibleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		board.getFen().incrementHalfMoveClock();
	}
}

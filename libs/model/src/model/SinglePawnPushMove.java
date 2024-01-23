package model;

import api.Square;

class SinglePawnPushMove extends PawnMove {

	SinglePawnPushMove(Square source, Square destination, IBoard board, IPiece pawn) {
		super(source, destination, board);
		pawn.getBitboard().setBitByIndex(Board.getIndex(destination));
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

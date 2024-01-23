package model;

import api.Pieces;
import api.Square;

class QueenPromotionCaptureMove extends PromotionCaptureMove {

	QueenPromotionCaptureMove(Square source, Square destination, IBoard board) {
		super(
			source,
			destination,
			board,
			board.getFen().isWhite() ? Pieces.WhiteQueen : Pieces.BlackQueen
		);
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

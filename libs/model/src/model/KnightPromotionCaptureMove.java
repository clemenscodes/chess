package model;

import api.model.Pieces;
import api.model.Square;

class KnightPromotionCaptureMove extends PromotionCaptureMove {

	KnightPromotionCaptureMove(Square source, Square destination, IBoard board) {
		super(
			source,
			destination,
			board,
			board.getFen().isWhite() ? Pieces.WhiteKnight : Pieces.BlackKnight
		);
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

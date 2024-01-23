package model;

import api.Pieces;
import api.Square;

class BishopPromotionMove extends PromotionMove {

	BishopPromotionMove(Square source, Square destination, IBoard board) {
		super(
			source,
			destination,
			board,
			board.getFen().isWhite() ? Pieces.WhiteBishop : Pieces.BlackBishop
		);
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

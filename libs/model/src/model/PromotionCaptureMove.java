package model;

import api.model.IBoard;
import api.model.Pieces;
import api.model.Square;

abstract class PromotionCaptureMove extends PromotionMove {

	PromotionCaptureMove(Square source, Square destination, IBoard board, Pieces chosenPromotion) {
		super(source, destination, board, chosenPromotion);
		board.capturePiece(Board.getIndex(destination));
		promote(board, destination, chosenPromotion);
	}
}

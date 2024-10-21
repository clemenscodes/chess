package model;

import api.Pieces;
import api.Square;

abstract class PromotionCaptureMove extends PromotionMove {

    PromotionCaptureMove(Square source, Square destination, IBoard board, Pieces chosenPromotion) {
        super(source, destination, board, chosenPromotion);
        board.capturePiece(Board.getIndex(destination));
        promote(board, destination, chosenPromotion);
    }
}

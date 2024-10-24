package model;

import api.Pieces;
import api.Square;

abstract class PromotionMove extends PawnMove {

    PromotionMove(Square source, Square destination, IBoard board, Pieces chosenPromotion) {
        super(source, destination, board);
        if (!(this instanceof PromotionCaptureMove)) {
            promote(board, destination, chosenPromotion);
        }
    }

    protected void promote(IBoard board, Square destination, Pieces chosenPromotion) {
        board
                .getPieceByKind(chosenPromotion)
                .getBitboard()
                .merge(Bitboard.getSingleBit(Board.getIndex(destination)));
    }
}

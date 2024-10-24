package model;

import api.Pieces;
import api.Square;

class RookPromotionCaptureMove extends PromotionCaptureMove {

    RookPromotionCaptureMove(Square source, Square destination, IBoard board) {
        super(
                source,
                destination,
                board,
                board.getFen().isWhite() ? Pieces.WhiteRook : Pieces.BlackRook);
    }

    @Override
    public String toString() {
        Square source = getSource();
        Square destination = getDestination();
        return String.valueOf(source) + destination;
    }
}

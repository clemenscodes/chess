package model;

import api.Square;

class PawnCaptureMove extends PawnMove {

    PawnCaptureMove(Square source, Square destination, IBoard board, IPiece pawn) {
        super(source, destination, board);
        board.capturePiece(Board.getIndex(destination));
        pawn.getBitboard().setBitByIndex(Board.getIndex(destination));
    }

    @Override
    public String toString() {
        Square source = getSource();
        Square destination = getDestination();
        return String.valueOf(source) + destination;
    }
}

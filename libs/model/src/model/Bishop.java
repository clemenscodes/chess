package model;

import api.Pieces;

abstract class Bishop extends Piece implements IBishop {

    Bishop(Pieces variant) {
        super(variant);
    }

    Bishop(Pieces variant, IBitboard board) {
        super(variant, board);
    }

    public IBitboard getAttacks(IBitboard piece, IBoard board) {
        return Bitboard.getDiagonalRays(piece, board);
    }
}

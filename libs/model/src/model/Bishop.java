package model;

import api.model.IBishop;
import api.model.IBitboard;
import api.model.IBoard;
import api.model.Pieces;

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

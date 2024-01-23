package model;

import api.Pieces;

abstract class Rook extends Piece implements IRook {

	Rook(Pieces variant) {
		super(variant);
	}

	Rook(Pieces variant, IBitboard board) {
		super(variant, board);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] {
				Bitboard.getHorizontalRays(piece, board),
				Bitboard.getVerticalRays(piece, board),
			}
		);
	}
}

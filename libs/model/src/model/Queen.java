package model;

import api.Pieces;

abstract class Queen extends Piece implements IQueen {

	Queen(Pieces variant) {
		super(variant);
	}

	Queen(Pieces variant, IBitboard board) {
		super(variant, board);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] {
				Bitboard.getDiagonalRays(piece, board),
				Bitboard.getHorizontalRays(piece, board),
				Bitboard.getVerticalRays(piece, board),
			}
		);
	}
}

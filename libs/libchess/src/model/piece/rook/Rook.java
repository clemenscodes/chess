package model.piece.rook;

import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Rook extends Piece implements IRook {

	public Rook(Pieces variant) {
		super(variant);
	}

	public Rook(Pieces variant, IBitboard board) {
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

package model.piece.rook;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Movable;
import model.piece.Piece;
import model.piece.Pieces;
import model.piece.Rays;

public abstract class Rook extends Piece implements Movable, Serializable {

	public Rook(Pieces variant) {
		super(variant);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] {
				Rays.getHorizontalRays(piece, board),
				Rays.getVerticalRays(piece, board),
			}
		);
	}
}

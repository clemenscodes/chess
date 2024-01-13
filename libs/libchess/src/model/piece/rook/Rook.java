package model.piece.rook;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Directions;
import model.piece.Movable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Rook extends Piece implements Movable, Serializable {

	public Rook(Pieces variant) {
		super(variant);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] {
				Directions.getHorizontalRays(piece, board),
				Directions.getVerticalRays(piece, board),
			}
		);
	}
}

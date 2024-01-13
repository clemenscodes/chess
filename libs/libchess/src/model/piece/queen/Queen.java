package model.piece.queen;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Directions;
import model.piece.Movable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Queen extends Piece implements Movable, Serializable {

	public Queen(Pieces variant) {
		super(variant);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] {
				Directions.getDiagonalRays(piece, board),
				Directions.getHorizontalRays(piece, board),
				Directions.getVerticalRays(piece, board),
			}
		);
	}
}

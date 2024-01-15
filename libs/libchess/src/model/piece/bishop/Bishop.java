package model.piece.bishop;

import java.io.Serializable;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Movable;
import model.piece.Piece;
import model.piece.Pieces;
import model.piece.Rays;

public abstract class Bishop extends Piece implements Movable, Serializable {

	public Bishop(Pieces variant) {
		super(variant);
	}

	public Bishop(Pieces variant, IBitboard board) {
		super(variant, board);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		return Rays.getDiagonalRays(piece, board);
	}
}

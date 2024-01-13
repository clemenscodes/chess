package model.piece.bishop;

import java.io.Serializable;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Directions;
import model.piece.Movable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Bishop extends Piece implements Movable, Serializable {

	public Bishop(Pieces variant) {
		super(variant);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		return Directions.getDiagonalRays(piece, board);
	}
}

package piece.bishop;

import api.model.bits.IBitboard;
import api.model.board.IBoard;
import api.model.piece.Pieces;
import api.model.piece.bishop.IBishop;
import bits.Bitboard;
import piece.Piece;

public abstract class Bishop extends Piece implements IBishop {

	public Bishop(Pieces variant) {
		super(variant);
	}

	public Bishop(Pieces variant, IBitboard board) {
		super(variant, board);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		return Bitboard.getDiagonalRays(piece, board);
	}
}

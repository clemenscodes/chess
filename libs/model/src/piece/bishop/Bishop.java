package piece.bishop;

import bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Pieces;
import model.piece.bishop.IBishop;
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

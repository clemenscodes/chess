package piece.queen;

import api.model.bits.IBitboard;
import api.model.board.IBoard;
import api.model.piece.Pieces;
import api.model.piece.queen.IQueen;
import bits.Bitboard;
import piece.Piece;

public abstract class Queen extends Piece implements IQueen {

	public Queen(Pieces variant) {
		super(variant);
	}

	public Queen(Pieces variant, IBitboard board) {
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

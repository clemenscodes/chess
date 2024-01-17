package piece.rook;

import api.model.bits.IBitboard;
import api.model.board.IBoard;
import api.model.piece.Pieces;
import api.model.piece.rook.IRook;
import bits.Bitboard;
import piece.Piece;

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

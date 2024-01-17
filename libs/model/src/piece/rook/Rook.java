package piece.rook;

import bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Pieces;
import model.piece.rook.IRook;
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

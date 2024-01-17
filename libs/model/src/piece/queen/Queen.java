package piece.queen;

import bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Pieces;
import model.piece.queen.IQueen;
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

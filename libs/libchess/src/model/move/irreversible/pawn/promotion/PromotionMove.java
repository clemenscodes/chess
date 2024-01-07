package model.move.irreversible.pawn.promotion;

import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.pawn.PawnMove;
import model.piece.Pieces;

public abstract class PromotionMove extends PawnMove {

	public PromotionMove(
		Square source,
		Square destination,
		IBoard board,
		IBitboard pawn,
		Pieces chosenPromotion
	) {
		super(source, destination, board);
		var src = Square.getIndex(source);
		var dst = Square.getIndex(destination);
		board.getPiece(chosenPromotion).getBitboard().merge(Bitboard.getSingleBit(src));
		pawn.toggleBits(Bitboard.getSingleBit(dst));
	}
}

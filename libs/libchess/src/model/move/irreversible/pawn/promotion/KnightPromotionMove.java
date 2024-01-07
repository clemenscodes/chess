package model.move.irreversible.pawn.promotion;

import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;
import model.piece.Pieces;

public class KnightPromotionMove extends PromotionMove {

	public KnightPromotionMove(
		Square source,
		Square destination,
		IBoard board,
		IPiece pawn,
		Pieces chosenPromotion
	) {
		super(source, destination, board, pawn, chosenPromotion);
	}
}

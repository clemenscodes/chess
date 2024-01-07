package model.move.irreversible.pawn.promotion;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;
import model.piece.Pieces;

public class BishopPromotionMove extends PromotionMove {

	public BishopPromotionMove(
		Square source,
		Square destination,
		IBoard board,
		IPiece pawn,
		Pieces chosenPromotion
	) {
		super(source, destination, board, pawn, chosenPromotion);
	}
}

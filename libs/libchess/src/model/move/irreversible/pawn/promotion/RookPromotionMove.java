package model.move.irreversible.pawn.promotion;

import model.board.IBoard;
import model.board.Square;
import model.piece.Pieces;

public class RookPromotionMove extends PromotionMove {

	public RookPromotionMove(
		Square source,
		Square destination,
		IBoard board,
		Pieces chosenPromotion
	) {
		super(source, destination, board, chosenPromotion);
	}
}

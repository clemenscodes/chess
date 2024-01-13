package model.move.irreversible.pawn.promotion.capturing;

import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.pawn.promotion.PromotionMove;
import model.piece.Pieces;

public abstract class PromotionCaptureMove extends PromotionMove {

	public PromotionCaptureMove(
		Square source,
		Square destination,
		IBoard board,
		Pieces chosenPromotion
	) {
		super(source, destination, board, chosenPromotion);
		board.capturePiece(Square.getIndex(destination));
		promote(board, destination, chosenPromotion);
	}
}

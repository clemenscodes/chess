package move.irreversible.pawn.promotion.capturing;

import api.model.board.IBoard;
import api.model.board.Square;
import api.model.piece.Pieces;
import board.Board;
import move.irreversible.pawn.promotion.PromotionMove;

public abstract class PromotionCaptureMove extends PromotionMove {

	public PromotionCaptureMove(
		Square source,
		Square destination,
		IBoard board,
		Pieces chosenPromotion
	) {
		super(source, destination, board, chosenPromotion);
		board.capturePiece(Board.getIndex(destination));
		promote(board, destination, chosenPromotion);
	}
}

package move.irreversible.pawn.promotion.capturing;

import board.Board;
import model.board.IBoard;
import model.board.Square;
import model.piece.Pieces;
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

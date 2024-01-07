package model.move.irreversible.pawn.promotion.capturing;

import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.pawn.promotion.PromotionMove;

public abstract class PromotionCaptureMove extends PromotionMove {

	public PromotionCaptureMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

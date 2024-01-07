package model.move.irreversible.pawn.promotion.capturing;

import model.board.IBoard;
import model.board.Square;

public class RookPromotionCaptureMove extends PromotionCaptureMove {

	public RookPromotionCaptureMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

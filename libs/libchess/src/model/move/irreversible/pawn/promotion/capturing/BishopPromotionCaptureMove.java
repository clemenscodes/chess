package model.move.irreversible.pawn.promotion.capturing;

import model.board.IBoard;
import model.board.Square;

public class BishopPromotionCaptureMove extends PromotionCaptureMove {

	public BishopPromotionCaptureMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

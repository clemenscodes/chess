package model.move.irreversible.pawn.promotion.capturing;

import model.board.IBoard;
import model.board.Square;

public class KnightPromotionCaptureMove extends PromotionCaptureMove {

	public KnightPromotionCaptureMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

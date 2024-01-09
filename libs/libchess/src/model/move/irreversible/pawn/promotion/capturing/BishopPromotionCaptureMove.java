package model.move.irreversible.pawn.promotion.capturing;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;
import model.piece.Pieces;

public class BishopPromotionCaptureMove extends PromotionCaptureMove {

	public BishopPromotionCaptureMove(
		Square source,
		Square destination,
		IBoard board,
		Pieces chosenPromotion
	) {
		super(source, destination, board, chosenPromotion);
	}
}

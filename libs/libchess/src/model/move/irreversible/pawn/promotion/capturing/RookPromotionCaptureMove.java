package model.move.irreversible.pawn.promotion.capturing;

import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;
import model.piece.Pieces;

public class RookPromotionCaptureMove extends PromotionCaptureMove {

	public RookPromotionCaptureMove(
		Square source,
		Square destination,
		IBoard board,
		IPiece pawn,
		Pieces chosenPromotion
	) {
		super(source, destination, board, pawn, chosenPromotion);
	}
}

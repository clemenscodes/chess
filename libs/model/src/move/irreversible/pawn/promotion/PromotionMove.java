package move.irreversible.pawn.promotion;

import api.model.board.IBoard;
import api.model.board.Square;
import api.model.piece.Pieces;
import bits.Bitboard;
import board.Board;
import move.irreversible.pawn.PawnMove;
import move.irreversible.pawn.promotion.capturing.PromotionCaptureMove;

public abstract class PromotionMove extends PawnMove {

	public PromotionMove(Square source, Square destination, IBoard board, Pieces chosenPromotion) {
		super(source, destination, board);
		if (!(this instanceof PromotionCaptureMove)) {
			promote(board, destination, chosenPromotion);
		}
	}

	protected void promote(IBoard board, Square destination, Pieces chosenPromotion) {
		board
			.getPieceByKind(chosenPromotion)
			.getBitboard()
			.merge(Bitboard.getSingleBit(Board.getIndex(destination)));
	}
}

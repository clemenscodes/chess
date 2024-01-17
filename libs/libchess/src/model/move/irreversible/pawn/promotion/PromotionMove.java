package model.move.irreversible.pawn.promotion;

import model.bits.Bitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.pawn.PawnMove;
import model.move.irreversible.pawn.promotion.capturing.PromotionCaptureMove;
import model.piece.Pieces;

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

package model.move.irreversible.pawn.promotion;

import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.pawn.PawnMove;
import model.piece.Pieces;

public abstract class PromotionMove extends PawnMove {

	public PromotionMove(Square source, Square destination, IBoard board, Pieces chosenPromotion) {
		super(source, destination, board);
		IBitboard promotionBoard = board.getPieceByKind(chosenPromotion).getBitboard();
		promotionBoard.merge(Bitboard.getSingleBit(Square.getIndex(destination)));
		board.getFen().updatePiecePlacementData(board);
	}
}

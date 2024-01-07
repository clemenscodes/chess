package model.move.irreversible.pawn.promotion;

import model.board.IBoard;
import model.board.Square;

public class RookPromotionMove extends PromotionMove {

	public RookPromotionMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

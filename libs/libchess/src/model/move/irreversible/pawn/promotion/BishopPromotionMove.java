package model.move.irreversible.pawn.promotion;

import model.board.IBoard;
import model.board.Square;

public class BishopPromotionMove extends PromotionMove {

	public BishopPromotionMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

package model.move.irreversible.pawn.promotion;

import model.board.IBoard;
import model.board.Square;

public class KnightPromotionMove extends PromotionMove {

	public KnightPromotionMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

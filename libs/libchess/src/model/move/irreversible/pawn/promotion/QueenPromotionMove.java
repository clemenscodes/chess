package model.move.irreversible.pawn.promotion;

import model.board.IBoard;
import model.board.Square;

public class QueenPromotionMove extends PromotionMove {

	public QueenPromotionMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

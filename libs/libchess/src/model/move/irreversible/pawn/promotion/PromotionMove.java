package model.move.irreversible.pawn.promotion;

import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.pawn.PawnMove;

public abstract class PromotionMove extends PawnMove {

	public PromotionMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

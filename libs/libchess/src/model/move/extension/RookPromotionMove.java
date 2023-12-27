package model.move.extension;

import model.board.Square;
import model.move.Move;
import model.move.Moves;

public class RookPromotionMove extends Move {

	public RookPromotionMove(Square source, Square destination) {
		super(source, destination, Moves.RookPromotion);
	}
}

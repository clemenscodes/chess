package model.move.extension;

import model.board.Square;
import model.move.Move;
import model.move.Moves;

public class KnightPromotionMove extends Move {

	public KnightPromotionMove(Square source, Square destination) {
		super(source, destination, Moves.KnightPromotion);
	}
}

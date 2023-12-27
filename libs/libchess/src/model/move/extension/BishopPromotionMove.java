package model.move.extension;

import model.board.Square;
import model.move.Move;
import model.move.Moves;

public class BishopPromotionMove extends Move {

	public BishopPromotionMove(Square source, Square destination) {
		super(source, destination, Moves.BishopPromotion);
	}
}

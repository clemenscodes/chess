package model.move.extension;

import model.board.Square;
import model.move.Move;

public class KnightPromotionMove extends Move {

	public KnightPromotionMove(Square source, Square destination) {
		super(source, destination);
	}
}

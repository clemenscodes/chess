package model.move.extension;

import model.board.Square;
import model.move.Move;

public class BishopPromotionMove extends Move {

	public BishopPromotionMove(Square source, Square destination) {
		super(source, destination);
	}
}

package model.move.extension;

import model.board.Square;
import model.move.Move;

public class QueenPromotionMove extends Move {

	public QueenPromotionMove(Square source, Square destination) {
		super(source, destination);
	}
}

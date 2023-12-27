package model.move.extension;

import model.board.Square;
import model.move.Move;

public class KnightPromotionCaptureMove extends Move {

	public KnightPromotionCaptureMove(Square source, Square destination) {
		super(source, destination);
	}
}

package model.move.extension;

import model.board.Square;
import model.move.Move;

public class RookPromotionCaptureMove extends Move {

	public RookPromotionCaptureMove(Square source, Square destination) {
		super(source, destination);
	}
}

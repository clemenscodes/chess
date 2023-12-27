package model.move.extension;

import model.board.Square;
import model.move.Move;

public class BishopPromotionCaptureMove extends Move {

	public BishopPromotionCaptureMove(Square source, Square destination) {
		super(source, destination);
	}
}

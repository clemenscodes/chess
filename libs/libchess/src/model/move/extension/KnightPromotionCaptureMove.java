package model.move.extension;

import model.board.Square;
import model.move.Move;
import model.move.Moves;

public class KnightPromotionCaptureMove extends Move {

	public KnightPromotionCaptureMove(Square source, Square destination) {
		super(source, destination, Moves.KnightPromotionCapture);
	}
}

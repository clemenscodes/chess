package model.move.extension;

import model.board.Square;
import model.move.Move;
import model.move.Moves;

public class QueenPromotionCaptureMove extends Move {

	public QueenPromotionCaptureMove(Square source, Square destination) {
		super(source, destination, Moves.QueenPromotionCapture);
	}
}

package model.move.extension;

import model.board.Square;
import model.move.Move;
import model.move.Moves;

public class CaptureMove extends Move {

	public CaptureMove(Square source, Square destination) {
		super(source, destination, Moves.Capture);
	}
}

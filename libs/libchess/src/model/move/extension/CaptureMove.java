package model.move.extension;

import model.board.Square;
import model.move.Move;

public class CaptureMove extends Move {

	public CaptureMove(Square source, Square destination) {
		super(source, destination);
	}
}

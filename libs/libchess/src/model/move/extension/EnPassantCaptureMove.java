package model.move.extension;

import model.board.Square;
import model.move.Move;

public class EnPassantCaptureMove extends Move {

	public EnPassantCaptureMove(Square source, Square destination) {
		super(source, destination);
	}
}

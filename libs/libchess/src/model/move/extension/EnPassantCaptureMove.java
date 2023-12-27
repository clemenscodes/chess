package model.move.extension;

import model.board.Square;
import model.move.Move;
import model.move.Moves;

public class EnPassantCaptureMove extends Move {

	public EnPassantCaptureMove(Square source, Square destination) {
		super(source, destination, Moves.EnPassantCapture);
	}
}

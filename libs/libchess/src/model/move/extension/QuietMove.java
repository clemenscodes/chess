package model.move.extension;

import model.board.Square;
import model.move.Move;
import model.move.Moves;

public class QuietMove extends Move {

	public QuietMove(Square source, Square destination) {
		super(source, destination, Moves.Quiet);
	}
}

package model.move.extension;

import model.board.Square;
import model.move.Move;

public class QuietMove extends Move {

	public QuietMove(Square source, Square destination) {
		super(source, destination);
	}
}

package model.move.extension;

import model.board.Square;
import model.move.Move;
import model.move.Moves;

public class DoublePawnPushMove extends Move {

	public DoublePawnPushMove(Square source, Square destination) {
		super(source, destination, Moves.DoublePawnPush);
	}
}

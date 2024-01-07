package model.move.reversible;

import model.board.IBoard;
import model.board.Square;
import model.move.Move;

public abstract class ReversibleMove extends Move {

	public ReversibleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		board.getFen().incrementHalfMoveClock();
	}
}

package move.reversible;

import model.board.IBoard;
import model.board.Square;
import move.Move;

public abstract class ReversibleMove extends Move {

	public ReversibleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		board.getFen().incrementHalfMoveClock();
	}
}

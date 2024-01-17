import api.model.board.IBoard;
import api.model.board.Square;

abstract class ReversibleMove extends Move {

	ReversibleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		board.getFen().incrementHalfMoveClock();
	}
}

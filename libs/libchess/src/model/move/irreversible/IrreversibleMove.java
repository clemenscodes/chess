package model.move.irreversible;

import model.board.IBoard;
import model.board.Square;
import model.move.Move;

public abstract class IrreversibleMove extends Move {

	public IrreversibleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		board.getFen().resetHalfMoveClock();
	}
}

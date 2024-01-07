package model.move.irreversible.pawn;

import model.board.IBoard;
import model.board.Square;

public class SinglePawnPushMove extends PawnMove {

	public SinglePawnPushMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

package model.move.irreversible.pawn;

import model.board.IBoard;
import model.board.Square;

public class DoublePawnPushMove extends PawnMove {

	public DoublePawnPushMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

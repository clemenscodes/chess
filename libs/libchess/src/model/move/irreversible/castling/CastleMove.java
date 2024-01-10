package model.move.irreversible.castling;

import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.IrreversibleMove;

public abstract class CastleMove extends IrreversibleMove {

	public CastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		if (board.kingUnsafe()) {
			throw new Error("Can not castle when king is in check");
		}
		board.getFen().castle();
	}
}

package move.irreversible.pawn;

import api.model.board.IBoard;
import api.model.board.Square;
import board.Board;
import move.irreversible.IrreversibleMove;

public abstract class PawnMove extends IrreversibleMove {

	public PawnMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		board.getPiece(source).getBitboard().unsetBitByIndex(Board.getIndex(source));
	}
}

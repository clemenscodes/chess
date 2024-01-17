package move.irreversible.pawn;

import board.Board;
import model.board.IBoard;
import model.board.Square;
import move.irreversible.IrreversibleMove;

public abstract class PawnMove extends IrreversibleMove {

	public PawnMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		board.getPiece(source).getBitboard().unsetBitByIndex(Board.getIndex(source));
	}
}

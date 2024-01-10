package model.move.irreversible.pawn;

import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.IrreversibleMove;

public abstract class PawnMove extends IrreversibleMove {

	public PawnMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		board.getPiece(source).getBitboard().unsetBitByIndex(Square.getIndex(source));
	}
}

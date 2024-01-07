package model.move.irreversible.pawn;

import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.IrreversibleMove;
import model.piece.IPiece;

public abstract class PawnMove extends IrreversibleMove {

	public PawnMove(Square source, Square destination, IBoard board, IPiece pawn) {
		super(source, destination, board);
		pawn.getBitboard().unsetBitByIndex(Square.getIndex(source));
	}
}

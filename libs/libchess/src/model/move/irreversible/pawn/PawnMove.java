package model.move.irreversible.pawn;

import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.IrreversibleMove;
import model.piece.IPiece;
import model.piece.pawn.Pawn;

public abstract class PawnMove extends IrreversibleMove {

	public PawnMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		IPiece pawn = board.getPiece(board.getPieceByIndex(Square.getIndex(source)));
		pawn.getBitboard().unsetBitByIndex(Square.getIndex(source));
	}
}

package model.move.irreversible.pawn;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

public class DoublePawnPushMove extends PawnMove {

	public DoublePawnPushMove(Square source, Square destination, IBoard board, IPiece pawn) {
		super(source, destination, board, pawn);
	}
}

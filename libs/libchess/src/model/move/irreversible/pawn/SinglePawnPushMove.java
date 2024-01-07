package model.move.irreversible.pawn;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

public class SinglePawnPushMove extends PawnMove {

	public SinglePawnPushMove(Square source, Square destination, IBoard board, IPiece pawn) {
		super(source, destination, board, pawn);
	}
}

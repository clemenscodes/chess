package move.irreversible.pawn;

import api.model.board.IBoard;
import api.model.board.Square;
import api.model.piece.IPiece;
import board.Board;

public class SinglePawnPushMove extends PawnMove {

	public SinglePawnPushMove(Square source, Square destination, IBoard board, IPiece pawn) {
		super(source, destination, board);
		pawn.getBitboard().setBitByIndex(Board.getIndex(destination));
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

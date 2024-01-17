package move.irreversible.pawn;

import board.Board;
import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

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

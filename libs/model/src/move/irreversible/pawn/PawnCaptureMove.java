package move.irreversible.pawn;

import api.model.board.IBoard;
import api.model.board.Square;
import api.model.piece.IPiece;
import board.Board;

public class PawnCaptureMove extends PawnMove {

	public PawnCaptureMove(Square source, Square destination, IBoard board, IPiece pawn) {
		super(source, destination, board);
		board.capturePiece(Board.getIndex(destination));
		pawn.getBitboard().setBitByIndex(Board.getIndex(destination));
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

package model.move.irreversible.pawn;

import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

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

package model.move.irreversible.pawn;

import model.board.IBoard;
import model.board.Square;

public class EnPassantCaptureMove extends PawnMove {

	public EnPassantCaptureMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

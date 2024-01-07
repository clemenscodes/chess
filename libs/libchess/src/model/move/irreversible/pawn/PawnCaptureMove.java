package model.move.irreversible.pawn;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

public class PawnCaptureMove extends PawnMove {

	public PawnCaptureMove(Square source, Square destination, IBoard board, IPiece pawn) {
		super(source, destination, board, pawn);
		board.capturePiece(Square.getIndex(destination));
		pawn.getBitboard().setBitByIndex(Square.getIndex(destination));
	}
}

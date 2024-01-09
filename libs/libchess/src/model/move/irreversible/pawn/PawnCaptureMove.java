package model.move.irreversible.pawn;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

public class PawnCaptureMove extends PawnMove {

	public PawnCaptureMove(Square source, Square destination, IBoard board, IPiece capturingPawn) {
		super(source, destination, board);
		board.capturePiece(Square.getIndex(destination));
		capturingPawn.getBitboard().setBitByIndex(Square.getIndex(destination));
	}
}

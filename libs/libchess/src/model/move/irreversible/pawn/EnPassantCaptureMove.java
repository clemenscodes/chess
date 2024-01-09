package model.move.irreversible.pawn;

import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;
import model.piece.pawn.Pawn;
import model.piece.pawn.WhitePawn;

public class EnPassantCaptureMove extends PawnMove {

	public EnPassantCaptureMove(
		Square source,
		Square destination,
		IBoard board,
		IPiece capturingPawn
	) {
		super(source, destination, board);
		int dst = Square.getIndex(destination);
		int passedSquareIndex = getPassedSquareIndex(dst, capturingPawn);
		board.capturePiece(passedSquareIndex);
		capturingPawn.getBitboard().setBitByIndex(dst);
	}

	private int getPassedSquareIndex(int destination, IPiece pawn) {
		return pawn instanceof WhitePawn ? destination + Board.SOUTH : destination + Board.NORTH;
	}
}

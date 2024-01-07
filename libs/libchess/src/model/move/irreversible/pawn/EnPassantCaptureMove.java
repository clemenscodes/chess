package model.move.irreversible.pawn;

import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;
import model.piece.pawn.WhitePawn;

public class EnPassantCaptureMove extends PawnMove {

	public EnPassantCaptureMove(Square source, Square destination, IBoard board, IPiece pawn) {
		super(source, destination, board, pawn);
		int passedSquareIndex = getPassedSquareIndex(Square.getIndex(destination), pawn);
		board.capturePiece(passedSquareIndex);
	}

	private int getPassedSquareIndex(int destination, IPiece pawn) {
		return pawn instanceof WhitePawn ? destination + Board.SOUTH : destination + Board.NORTH;
	}
}

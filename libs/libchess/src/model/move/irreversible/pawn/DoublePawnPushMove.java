package model.move.irreversible.pawn;

import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;
import model.piece.pawn.WhitePawn;

public class DoublePawnPushMove extends PawnMove {

	public DoublePawnPushMove(Square source, Square destination, IBoard board, IPiece pawn) {
		super(source, destination, board);
		Square enPassantTargetSquare = getEnPassantTargetSquare(destination, pawn);
		board.getFen().setEnPassantTargetSquare(enPassantTargetSquare);
		pawn.getBitboard().setBitByIndex(Square.getIndex(destination));
	}

	private Square getEnPassantTargetSquare(Square destination, IPiece pawn) {
		int destinationIndex = Square.getIndex(destination);
		int enPassantTargetIndex = pawn instanceof WhitePawn
			? destinationIndex + Board.SOUTH
			: destinationIndex + Board.NORTH;
		return Square.getSquare(enPassantTargetIndex);
	}
}

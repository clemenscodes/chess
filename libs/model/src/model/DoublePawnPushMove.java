package model;

import api.Square;

class DoublePawnPushMove extends PawnMove {

	DoublePawnPushMove(Square source, Square destination, IBoard board, IPiece pawn) {
		super(source, destination, board);
		Square enPassantTargetSquare = getEnPassantTargetSquare(destination, pawn);
		board.getFen().setEnPassantTargetSquare(enPassantTargetSquare);
		pawn.getBitboard().setBitByIndex(Board.getIndex(destination));
	}

	private Square getEnPassantTargetSquare(Square destination, IPiece pawn) {
		int destinationIndex = Board.getIndex(destination);
		int enPassantTargetIndex = pawn instanceof WhitePawn
			? destinationIndex + Board.SOUTH
			: destinationIndex + Board.NORTH;
		return Board.getSquare(enPassantTargetIndex);
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

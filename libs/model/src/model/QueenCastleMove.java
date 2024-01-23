package model;

import static api.Square.*;

import api.Square;

class QueenCastleMove extends CastleMove {

	static final Square whiteQueenCastleDestinationSquare = c1;
	static final Square blackQueenCastleDestinationSquare = c8;
	private static final Square whiteQueenCastleRookSquare = a1;
	private static final Square whiteQueenCastleRookDestinationSquare = d1;
	private static final Square blackQueenCastleRookSquare = a8;
	private static final Square blackQueenCastleRookDestinationSquare = d8;

	QueenCastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}

	protected Square getRookSquare(IBoard board) {
		return board.getFen().isWhite() ? whiteQueenCastleRookSquare : blackQueenCastleRookSquare;
	}

	protected Square getCastledKingSquare(IBoard board) {
		return board.getFen().isWhite()
			? whiteQueenCastleDestinationSquare
			: blackQueenCastleDestinationSquare;
	}

	protected Square getCastledRookSquare(IBoard board) {
		return board.getFen().isWhite()
			? whiteQueenCastleRookDestinationSquare
			: blackQueenCastleRookDestinationSquare;
	}

	@Override
	public String toString() {
		return "O-O-O";
	}
}

package model.move.irreversible.castling;

import static model.board.Square.*;
import static model.board.Square.d8;

import model.board.IBoard;
import model.board.Square;

public class QueenCastleMove extends CastleMove {

	public static final Square whiteQueenCastleDestinationSquare = c1;
	public static final Square blackQueenCastleDestinationSquare = c8;
	private static final Square whiteQueenCastleRookSquare = a1;
	private static final Square whiteQueenCastleRookDestinationSquare = d1;
	private static final Square blackQueenCastleRookSquare = a8;
	private static final Square blackQueenCastleRookDestinationSquare = d8;

	public QueenCastleMove(Square source, Square destination, IBoard board) {
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

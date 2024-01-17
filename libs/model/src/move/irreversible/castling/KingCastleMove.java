package model.move.irreversible.castling;

import static model.board.Square.*;
import static model.board.Square.h8;

import model.board.IBoard;
import model.board.Square;

public class KingCastleMove extends CastleMove {

	public static final Square whiteKingCastleDestinationSquare = g1;
	public static final Square blackKingCastleDestinationSquare = g8;
	private static final Square whiteRookKingCastleDestinationSquare = f1;
	private static final Square whiteKingCastleRookSquare = h1;
	private static final Square blackRookKingCastleDestinationSquare = f8;
	private static final Square blackKingCastleRookSquare = h8;

	public KingCastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}

	protected Square getRookSquare(IBoard board) {
		return board.getFen().isWhite() ? whiteKingCastleRookSquare : blackKingCastleRookSquare;
	}

	protected Square getCastledKingSquare(IBoard board) {
		return board.getFen().isWhite()
			? whiteKingCastleDestinationSquare
			: blackKingCastleDestinationSquare;
	}

	protected Square getCastledRookSquare(IBoard board) {
		return board.getFen().isWhite()
			? whiteRookKingCastleDestinationSquare
			: blackRookKingCastleDestinationSquare;
	}

	@Override
	public String toString() {
		return "O-O";
	}
}

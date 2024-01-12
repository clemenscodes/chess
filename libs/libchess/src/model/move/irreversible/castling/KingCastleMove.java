package model.move.irreversible.castling;

import model.board.IBoard;
import model.board.Square;

public class KingCastleMove extends CastleMove {

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

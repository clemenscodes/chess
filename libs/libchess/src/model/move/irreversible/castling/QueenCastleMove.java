package model.move.irreversible.castling;

import model.board.IBoard;
import model.board.Square;

public class QueenCastleMove extends CastleMove {

	public QueenCastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}

	protected Square getRookSquare(IBoard board) {
		return board.getFen().isWhite() ? whiteQueenCastleRookSquare : blackQueenCastleRookSquare;
	}

	protected Square getCastledKingSquare(IBoard board) {
		return board.getFen().isWhite()
			? whiteQueenCastleDestinationSquare
			: blackKingCastleDestinationSquare;
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

package move.irreversible.pawn.promotion;

import api.model.board.IBoard;
import api.model.board.Square;
import api.model.piece.Pieces;

public class KnightPromotionMove extends PromotionMove {

	public KnightPromotionMove(Square source, Square destination, IBoard board) {
		super(
			source,
			destination,
			board,
			board.getFen().isWhite() ? Pieces.WhiteKnight : Pieces.BlackKnight
		);
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}
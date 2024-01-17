package move.irreversible.pawn.promotion;

import api.model.board.IBoard;
import api.model.board.Square;
import api.model.piece.Pieces;

public class BishopPromotionMove extends PromotionMove {

	public BishopPromotionMove(Square source, Square destination, IBoard board) {
		super(
			source,
			destination,
			board,
			board.getFen().isWhite() ? Pieces.WhiteBishop : Pieces.BlackBishop
		);
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

package move.irreversible.pawn.promotion;

import model.board.IBoard;
import model.board.Square;
import model.piece.Pieces;

public class RookPromotionMove extends PromotionMove {

	public RookPromotionMove(Square source, Square destination, IBoard board) {
		super(
			source,
			destination,
			board,
			board.getFen().isWhite() ? Pieces.WhiteRook : Pieces.BlackRook
		);
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

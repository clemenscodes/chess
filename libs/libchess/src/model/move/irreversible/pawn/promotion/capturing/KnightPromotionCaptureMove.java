package model.move.irreversible.pawn.promotion.capturing;

import model.board.IBoard;
import model.board.Square;
import model.piece.Pieces;

public class KnightPromotionCaptureMove extends PromotionCaptureMove {

	public KnightPromotionCaptureMove(Square source, Square destination, IBoard board) {
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

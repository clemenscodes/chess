import api.model.board.IBoard;
import api.model.board.Square;
import api.model.piece.Pieces;

class QueenPromotionMove extends PromotionMove {

	QueenPromotionMove(Square source, Square destination, IBoard board) {
		super(
			source,
			destination,
			board,
			board.getFen().isWhite() ? Pieces.WhiteQueen : Pieces.BlackQueen
		);
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

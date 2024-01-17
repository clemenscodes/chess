import api.model.board.IBoard;
import api.model.board.Square;
import api.model.piece.Pieces;

class KnightPromotionCaptureMove extends PromotionCaptureMove {

	KnightPromotionCaptureMove(Square source, Square destination, IBoard board) {
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

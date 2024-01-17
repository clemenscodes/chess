import api.model.board.IBoard;
import api.model.board.Square;
import api.model.piece.Pieces;

abstract class PromotionCaptureMove extends PromotionMove {

	PromotionCaptureMove(Square source, Square destination, IBoard board, Pieces chosenPromotion) {
		super(source, destination, board, chosenPromotion);
		board.capturePiece(Board.getIndex(destination));
		promote(board, destination, chosenPromotion);
	}
}

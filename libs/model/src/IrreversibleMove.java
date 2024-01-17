import api.model.board.IBoard;
import api.model.board.Square;
import api.model.piece.IPiece;

abstract class IrreversibleMove extends Move {

	IrreversibleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		IPiece piece = board.getPiece(source);
		if (piece instanceof King) {
			board.getFen().incrementHalfMoveClock();
		} else {
			board.getFen().resetHalfMoveClock();
		}
	}
}

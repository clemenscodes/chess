package move.irreversible;

import api.model.board.IBoard;
import api.model.board.Square;
import api.model.piece.IPiece;
import move.Move;
import piece.king.King;

public abstract class IrreversibleMove extends Move {

	public IrreversibleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		IPiece piece = board.getPiece(source);
		if (piece instanceof King) {
			board.getFen().incrementHalfMoveClock();
		} else {
			board.getFen().resetHalfMoveClock();
		}
	}
}

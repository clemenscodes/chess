package move.irreversible;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;
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

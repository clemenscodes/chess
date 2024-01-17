package model.move.irreversible;

import model.board.IBoard;
import model.board.Square;
import model.move.Move;
import model.piece.IPiece;
import model.piece.king.King;

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

package model.move.irreversible.castling;

import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.IrreversibleMove;

public class KingCastleMove extends IrreversibleMove {

	public KingCastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		board.getFen().castle();
	}
}

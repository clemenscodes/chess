package model.move.irreversible.castling;

import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.IrreversibleMove;

public class QueenCastleMove extends IrreversibleMove {

	public QueenCastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}
}

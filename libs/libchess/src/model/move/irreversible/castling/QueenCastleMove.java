package model.move.irreversible.castling;

import static model.board.Square.*;

import model.board.IBoard;
import model.board.Square;

public class QueenCastleMove extends CastleMove {

	public QueenCastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}

	protected Square getCastledKingSquare(IBoard board) {
		return board.getFen().getActiveColor() == 'w' ? c1 : c8;
	}

	protected Square getCastledRookSquare(IBoard board) {
		return board.getFen().getActiveColor() == 'w' ? d1 : d8;
	}

	@Override
	public String toString() {
		return "O-O-O";
	}
}

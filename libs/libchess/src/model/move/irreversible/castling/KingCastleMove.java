package model.move.irreversible.castling;

import static model.board.Square.*;

import model.board.IBoard;
import model.board.Square;

public class KingCastleMove extends CastleMove {

	public KingCastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}

	protected Square getCastledKingSquare(IBoard board) {
		return board.getFen().getActiveColor() == 'w' ? f1 : f8;
	}

	protected Square getCastledRookSquare(IBoard board) {
		return board.getFen().getActiveColor() == 'w' ? g1 : g8;
	}

	@Override
	public String toString() {
		return "O-O";
	}
}

package model.move.extension;

import model.board.Square;
import model.move.Move;
import model.move.Moves;

public class QueenCastleMove extends Move {

	public QueenCastleMove(Square source, Square destination) {
		super(source, destination, Moves.QueenCastle);
	}
}

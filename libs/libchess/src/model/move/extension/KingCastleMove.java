package model.move.extension;

import model.board.Square;
import model.move.Move;
import model.move.Moves;

public class KingCastleMove extends Move {

	public KingCastleMove(Square source, Square destination) {
		super(source, destination, Moves.KingCastle);
	}
}

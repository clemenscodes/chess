package model.move.extension;

import model.board.Square;
import model.move.Move;

public class QueenCastleMove extends Move {

	public QueenCastleMove(Square source, Square destination) {
		super(source, destination);
	}
}

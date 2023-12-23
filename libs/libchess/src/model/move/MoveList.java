package model.move;

import java.io.Serializable;
import model.Square;

public class MoveList implements IMoveList, Serializable {

	public static final int MOVE_LIMIT = 17697;
	private final IMove[] moves;
	private int playedMoves;

	public MoveList() {
		playedMoves = 0;
		moves = new Move[MOVE_LIMIT];
	}

	public void move(Square source, Square destination) {
		if (playedMoves < MOVE_LIMIT) {
			moves[playedMoves++] = new Move(source, destination);
		}
	}

	public void unmove() {
		if (playedMoves > 0) {
			moves[--playedMoves] = null;
		}
	}

	public int getPlayedMoves() {
		return playedMoves;
	}

	public IMove[] getMoves() {
		return moves;
	}
}

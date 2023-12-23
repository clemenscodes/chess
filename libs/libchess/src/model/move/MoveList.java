package model.move;

import java.io.Serializable;
import model.enums.Square;

public class MoveList implements Serializable {

	public static final int MOVE_LIMIT = 17697;
	private final Move[] moves;
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

	public Move[] getMoves() {
		return moves;
	}
}

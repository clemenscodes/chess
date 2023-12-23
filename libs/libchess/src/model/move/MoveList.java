package model.move;

import java.io.Serializable;
import model.Square;
import model.board.IBoard;

public class MoveList implements IMoveList, Serializable {

	public static final int MOVE_LIMIT = 17697;
	private int playedMoves;
	private final IMove[] moves;

	public MoveList() {
		playedMoves = 0;
		moves = new Move[MOVE_LIMIT];
	}

	public int getPlayedMoves() {
		return playedMoves;
	}

	public IMove[] getMoves() {
		return moves;
	}

	public IBoard makeMove(IMove move, IBoard board) {
		if (playedMoves >= MOVE_LIMIT) {
			return board;
		}
		Square source = move.getSource();
		Square destination = move.getDestination();
		moves[playedMoves++] = move;
		return board;
	}

	public IBoard unmakeMove(IBoard board) {
		if (playedMoves <= 0) {
			return board;
		}
		moves[--playedMoves] = null;
		return board;
	}
}

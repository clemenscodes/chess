package model.piece;

import model.board.IBoard;

public interface Movable {
	IBoard move(int source, int destination, IBoard board);
	boolean isInvalidMove(int source, int destination, IBoard board);
}

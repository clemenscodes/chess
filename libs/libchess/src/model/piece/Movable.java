package model.piece;

import model.board.IBoard;

public interface Movable {
	void move(int source, int destination, IBoard board);
}

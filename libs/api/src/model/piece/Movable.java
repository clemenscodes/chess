package model.piece;

import model.board.IBoard;
import model.move.IMove;

public interface Movable {
	IMove move(int source, int destination, IBoard board);
}

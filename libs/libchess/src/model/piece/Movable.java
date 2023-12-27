package model.piece;

import model.board.IBoard;
import model.move.Moves;

public interface Movable {
	Moves move(int source, int destination, IBoard board);
}

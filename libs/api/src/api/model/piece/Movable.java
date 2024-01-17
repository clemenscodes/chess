package api.model.piece;

import api.model.board.IBoard;
import api.model.move.IMove;

public interface Movable {
	IMove move(int source, int destination, IBoard board);
}

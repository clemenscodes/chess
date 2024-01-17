package api.model.piece;

import api.model.board.IBoard;
import api.model.move.IMove;
import api.model.reader.IReader;

public interface MovableWithReader {
	IMove move(int source, int destination, IBoard board, IReader reader);
}

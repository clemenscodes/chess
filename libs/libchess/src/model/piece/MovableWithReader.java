package model.piece;

import model.board.IBoard;
import model.util.io.reader.IReader;

public interface MovableWithReader {
	void move(int source, int destination, IBoard board, IReader reader);
}

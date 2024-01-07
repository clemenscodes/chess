package model.piece;

import model.board.IBoard;
import model.move.IMove;
import model.reader.IReader;

public interface MovableWithReader {
	IMove move(int source, int destination, IBoard board, IReader reader);
}

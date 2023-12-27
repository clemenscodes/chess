package model.piece;

import model.board.IBoard;
import model.move.Moves;
import model.util.io.reader.IReader;

public interface MovableWithReader {
	Moves move(int source, int destination, IBoard board, IReader reader);
}

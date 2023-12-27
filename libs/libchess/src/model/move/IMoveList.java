package model.move;

import model.board.IBoard;
import model.board.Square;
import model.util.io.reader.IReader;

public interface IMoveList {
	void makeMove(Square source, Square destination, IBoard board, IReader reader);

	void unmakeMove(IBoard board);

	int getPlayedMoves();

	IMove[] getMoves();
}

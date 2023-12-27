package model.move;

import model.board.IBoard;
import model.util.io.reader.IReader;

public interface IMoveList {
	void makeMove(IMove move, IBoard board, IReader reader);

	void unmakeMove(IBoard board);

	int getPlayedMoves();

	IMove[] getMoves();
}

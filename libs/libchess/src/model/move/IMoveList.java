package model.move;

import model.board.IBoard;
import model.board.Square;
import model.reader.IReader;

public interface IMoveList {
	void makeMove(Square source, Square destination, IBoard board, IReader reader);

	int getPlayedMoves();

	IMove[] getMoves();
}

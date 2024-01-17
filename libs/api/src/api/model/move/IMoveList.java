package api.model.move;

import api.model.board.IBoard;
import api.model.board.Square;
import api.model.reader.IReader;

public interface IMoveList {
	void makeMove(Square source, Square destination, IBoard board, IReader reader);

	int getPlayedMoves();
}

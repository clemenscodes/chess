package api.model;

public interface IMoveList {
	void makeMove(Square source, Square destination, IBoard board, IReader reader);

	int getPlayedMoves();
}

package model.move;

import model.board.IBoard;

public interface IMoveList {
	void makeMove(IMove move, IBoard board);

	void unmakeMove(IBoard board);

	int getPlayedMoves();

	IMove[] getMoves();
}

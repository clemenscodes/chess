package model.move;

import model.board.IBoard;

public interface IMoveList {
	IBoard makeMove(IMove move, IBoard board);

	IBoard unmakeMove(IBoard board);

	int getPlayedMoves();

	IMove[] getMoves();
}

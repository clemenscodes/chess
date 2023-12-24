package model;

import model.board.IBoard;
import model.fen.IForsythEdwardsNotation;
import model.move.IMove;
import model.move.IMoveList;

public interface IChessModel {
	State getGameState();

	IForsythEdwardsNotation getFen();

	IBoard getBoard();

	IMoveList getMoveList();

	void startGame();

	void startNewGame();

	void makeMove(IMove move);

	void unmakeMove();
}

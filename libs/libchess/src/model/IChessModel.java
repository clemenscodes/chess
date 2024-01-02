package model;

import model.board.IBoard;
import model.board.Square;
import model.move.IMoveList;
import model.reader.IReader;

public interface IChessModel {
	State getGameState();

	IBoard getBoard();

	IMoveList getMoveList();

	IReader getReader();

	void startGame();

	void startNewGame();

	void makeMove(Square source, Square destination);
}

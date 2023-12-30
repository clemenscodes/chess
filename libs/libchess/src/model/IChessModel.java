package model;

import model.board.IBoard;
import model.board.Square;
import model.fen.IForsythEdwardsNotation;
import model.move.IMoveList;
import model.util.io.reader.IReader;

public interface IChessModel {
	State getGameState();

	IForsythEdwardsNotation getFen();

	IBoard getBoard();

	IMoveList getMoveList();

	IReader getReader();

	void startGame();

	void startNewGame();

	void makeMove(Square source, Square destination);

	void initUci();
}

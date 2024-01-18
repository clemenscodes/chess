package api.model;

import api.IChess;

public interface IChessModel extends IChess {
	State getGameState();

	void startGame();

	void startGame(String fen);

	void startNewGame();

	void resign();

	void makeMove(Square source, Square destination);

	boolean isCheckmate();

	boolean isStalemate();
}

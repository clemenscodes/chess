package api;

import api.model.Square;
import api.model.State;

public interface IGame {
	void startGame();

	void startNewGame();

	State getGameState();

	void resign();

	void makeMove(Square source, Square destination);

	boolean isCheckmate();

	boolean isStalemate();
}

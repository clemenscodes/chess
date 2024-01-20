package api;

import api.model.Square;
import api.model.State;

public interface IGame {
	State getGameState();

	String getMoves();

	void startGame();

	void startNewGame();

	void resign();

	void makeMove(Square source, Square destination);
}

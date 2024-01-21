package api;

import api.model.Square;
import api.model.State;
import java.util.ArrayList;

public interface IGame {
	void startGame();

	void startNewGame();

	void resign();

	void offerDraw();

	State getGameState();

	String getMoves();

	ArrayList<Square[]> getLegalMoves(Square square);

	void makeMove(Square source, Square destination);
}

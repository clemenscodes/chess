package api;

import api.model.Square;
import api.model.State;
import java.util.ArrayList;

public interface IGame {
	void startGame();

	void startNewGame();

	void resign();

	void offerDraw();

	void claimDraw();

	State getGameState();

	String getMoves();

	void makeMove(Square source, Square destination);

	boolean isOwnPieceOnSquare(Square square);
}

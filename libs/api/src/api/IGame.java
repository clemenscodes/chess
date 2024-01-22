package api;

import api.model.Square;
import api.model.State;

public interface IGame {
	void startGame();

	void startNewGame();

	void resign();

	void offerDraw();

	void acceptDraw();

	void declineDraw();

	void claimDraw();

	State getGameState();

	String getMoves();

	void makeMove(Square source, Square destination);

	void promoteQueen();

	void promoteRook();

	void promoteKnight();

	void promoteBishop();

	boolean isOwnPieceOnSquare(Square square);

	String getErrorMessage();
}

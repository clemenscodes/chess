package api.model;

public interface IChessModel {
	State getGameState();

	void startGame();

	String getFen();

	void startGame(String fen);

	void startNewGame();

	void resign();

	void makeMove(Square source, Square destination);

	boolean isCheckmate();

	boolean isStalemate();
}

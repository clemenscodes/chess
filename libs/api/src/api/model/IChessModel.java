package api.model;

public interface IChessModel {
	State getGameState();

	IBoard getBoard();

	IMoveList getMoveList();

	void startGame();

	void startGame(IBoard board);

	void startNewGame();

	void resign();

	void makeMove(Square source, Square destination);

	boolean isCheckmate();

	boolean isStalemate();
}

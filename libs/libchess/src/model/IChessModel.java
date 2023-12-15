package model;

public interface IChessModel {
	void startGame(int width, int height);

	void startNewGame(int width, int height);

	GameState getGameState();
}

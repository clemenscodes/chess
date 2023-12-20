package model;

import model.enums.GameState;

public interface IChessModel {
	GameState getGameState();

	void startGame(int width, int height);

	void startNewGame(int width, int height);

	void printBoard();
}

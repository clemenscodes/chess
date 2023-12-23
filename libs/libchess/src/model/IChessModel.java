package model;

import model.enums.GameState;

public interface IChessModel {
	GameState getGameState();

	void startGame();

	void startNewGame();
}

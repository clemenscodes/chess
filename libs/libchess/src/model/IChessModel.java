package model;

import model.data.GameState;
import model.data.player.White;

public interface IChessModel {
	void startGame(int width, int height);

	void startNewGame(int width, int height);

	GameState getGameState();
}

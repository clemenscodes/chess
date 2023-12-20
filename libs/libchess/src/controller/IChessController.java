package controller;

import model.enums.GameState;

public interface IChessController {
	void nextFrame();

	void handleUserInput(char key, int keyCode);

	void startGame(int width, int height);

	GameState getGameState();
}

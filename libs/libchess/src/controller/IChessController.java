package controller;

import model.State;

public interface IChessController {
	void nextFrame();

	void handleUserInput(char key, int keyCode);

	void startGame();
	void startNewGame();

	State getGameState();
}

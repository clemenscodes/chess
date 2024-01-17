package controller;

import model.IChessModel;
import model.State;
import view.IChessView;

public interface IChessController {
	void setModel(IChessModel model);

	void setView(IChessView view);

	void nextFrame();

	void handleUserInput(char key, int keyCode);

	void startGame();

	void startNewGame();

	State getGameState();
}

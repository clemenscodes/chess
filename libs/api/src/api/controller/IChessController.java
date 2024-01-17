package api.controller;

import api.model.IChessModel;
import api.model.State;
import api.view.IChessView;

public interface IChessController {
	void setModel(IChessModel model);

	void setView(IChessView view);

	void nextFrame();

	void handleUserInput(char key, int keyCode);

	void startGame();

	void startNewGame();

	State getGameState();
}

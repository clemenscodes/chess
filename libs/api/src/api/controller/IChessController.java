package api.controller;

import api.IChess;
import api.IGame;
import api.model.IChessModel;
import api.view.IChessView;

public interface IChessController extends IChess, IGame {
	void setModel(IChessModel model);

	void setView(IChessView view);

	void nextFrame();

	void handleUserInput(char key, int keyCode);
}

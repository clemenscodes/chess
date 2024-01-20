package api.controller;

import api.IChess;
import api.IGame;
import api.model.IChessModel;
import api.model.Square;
import api.view.IChessView;

public interface IChessController extends IChess, IGame {
	void setModel(IChessModel model);

	void setView(IChessView view);

	void nextFrame();

	void handleUserInput(int x, int y);

	String getErrorMessage();

	void clearErrorMessage();

	String getFen();
}

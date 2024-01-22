package api.controller;

import api.IChess;
import api.IGame;
import api.model.IChessModel;
import api.model.Square;
import api.view.IChessView;
import java.util.ArrayList;

public interface IChessController extends IChess, IGame {
	void setModel(IChessModel model);

	void setView(IChessView view);

	void nextFrame();

	void handleMousePressed(int x, int y);

	void handleMouseDragged(int x, int y);

	void handleMouseReleased(int x, int y);

	void handleMouseMoved(int x, int y);

	String getErrorMessage();

	void clearErrorMessage();

	String getFen();

	Square getSource();

	Square getDestination();

	Square getDraggedSquare();

	ArrayList<Square[]> getLegalMoves();
}

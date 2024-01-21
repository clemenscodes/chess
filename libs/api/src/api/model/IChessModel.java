package api.model;

import api.IChess;
import api.IGame;
import java.util.ArrayList;

public interface IChessModel extends IChess, IGame {
	String getFen();
	ArrayList<Square[]> getLegalMoves(Square square);
	void clearError();
}

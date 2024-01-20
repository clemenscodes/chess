package api.model;

import api.IChess;
import api.IGame;

public interface IChessModel extends IChess, IGame {
	String getFen();
}

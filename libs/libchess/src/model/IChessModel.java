package model;

import model.board.Board;

public interface IChessModel {
	void startGame(int width, int height);

	void startNewGame(int width, int height);

	GameState getGameState();

	Board getBoard();
}

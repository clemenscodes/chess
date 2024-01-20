package api.view;

import api.controller.IChessController;

public interface IChessView {
	void setController(IChessController controller);

	void setBackground();

	int getLeftBoardOffset();

	int getTopBoardOffset();

	int getSquareSize();

	int getWidth();

	int getHeight();

	void drawStart();

	void drawPlaying();

	void drawCheckmate();

	void drawStalemate();

	void drawGameOver();
}

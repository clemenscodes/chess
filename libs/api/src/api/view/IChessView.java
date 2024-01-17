package api.view;

import api.controller.IChessController;

public interface IChessView {
	void setController(IChessController controller);

	void setBackground();

	void drawStart();

	void drawPlaying();

	void drawCheckmate();

	void drawStalemate();

	void drawGameOver();
}

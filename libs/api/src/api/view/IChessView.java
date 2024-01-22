package api.view;

import api.controller.IChessController;

public interface IChessView {
	void setController(IChessController controller);

	int getLeftBoardOffset();

	int getTopBoardOffset();

	int getSquareSize();

	int getWidth();

	int getHeight();

	void drawStart();

	void drawPlaying();

	void drawCheckmate();

	void drawStalemate();

	void drawResignation();

	void drawDraw();

	void drawPromotion();

	void drawDrawOffer();
}

package model;

import api.IChess;
import api.model.Square;

interface IForsythEdwardsNotation extends IChess {
	IBitboard getEnPassantMask();

	void updatePiecePlacementData(IBoard board);

	void incrementFullMoveNumber();

	void incrementHalfMoveClock();

	void resetHalfMoveClock();

	void switchActiveColor();

	void setEnPassantTargetSquare(Square square);

	void unsetEnPassantTargetSquare();

	void parse(String fen);

	void castle();

	void kingMove();

	void kingRookMove();

	void queenRookMove();
}
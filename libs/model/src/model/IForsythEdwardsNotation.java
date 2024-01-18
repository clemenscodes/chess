package model;

import api.model.Square;

interface IForsythEdwardsNotation {
	String[] getPiecePlacementData();

	char getActiveColor();

	boolean isWhite();

	String getCastling();

	boolean getWhiteKingCastle();

	boolean getWhiteQueenCastle();

	boolean getBlackKingCastle();

	boolean getBlackQueenCastle();

	String getEnPassant();

	IBitboard getEnPassantMask();

	int getHalfMoveClock();

	int getFullMoveNumber();

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

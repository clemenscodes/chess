package model.fen;

import model.bits.IBitboard;
import model.board.Square;

public interface IForsythEdwardsNotation {
	String[] getPiecePlacementData();

	char getActiveColor();

	String getCastling();

	String getEnPassant();

	IBitboard getEnPassantMask();

	int getHalfMoveClock();

	int getFullMoveNumber();

	void incrementFullMoveNumber();

	void incrementHalfMoveClock();

	void resetHalfMoveClock();

	void switchActiveColor();

	void setEnPassantTargetSquare(Square square);

	void unsetEnPassantTargetSquare();

	void parse(String fen);
}

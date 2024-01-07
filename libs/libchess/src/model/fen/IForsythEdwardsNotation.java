package model.fen;

public interface IForsythEdwardsNotation {
	String[] getPiecePlacementData();

	char getActiveColor();

	String getCastling();

	String getEnPassant();

	int getHalfMoveClock();

	int getFullMoveNumber();

	void incrementFullMoveNumber();

	void incrementHalfMoveClock();

	void resetHalfMoveClock();
	void switchActiveColor();

	void parse(String fen);
}

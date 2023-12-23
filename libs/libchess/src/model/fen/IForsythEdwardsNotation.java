package model.fen;

public interface IForsythEdwardsNotation {
	String[] getPiecePlacementData();

	char getActiveColor();

	String getCastling();

	String getEnPassant();

	int getHalfMoveClock();

	int getFullMoveNumber();

	void parse(String fen);
}

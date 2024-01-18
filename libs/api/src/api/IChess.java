package api;

public interface IChess {
	String[] getPiecePlacementData();

	char getActiveColor();

	boolean isWhite();

	String getCastling();

	boolean getWhiteKingCastle();

	boolean getWhiteQueenCastle();

	boolean getBlackKingCastle();

	boolean getBlackQueenCastle();

	String getEnPassant();

	int getHalfMoveClock();

	int getFullMoveNumber();
}

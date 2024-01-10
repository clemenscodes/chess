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

	boolean getWhiteKingCastle();
	boolean getWhiteQueenCastle();
	boolean getBlackKingCastle();
	boolean getBlackQueenCastle();

	void whiteCastle();

	void whiteKingMove();

	void whiteKingRookMove();

	void whiteQueenRookMove();

	void blackCastle();

	void blackKingMove();

	void blackKingRookMove();

	void blackQueenRookMove();
}

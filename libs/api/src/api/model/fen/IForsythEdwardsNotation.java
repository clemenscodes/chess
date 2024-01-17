package api.model.fen;

import api.model.bits.IBitboard;
import api.model.board.IBoard;
import api.model.board.Square;

public interface IForsythEdwardsNotation {
	String[] getPiecePlacementData();

	char getActiveColor();

	boolean isWhite();

	String getCastling();

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

	boolean getWhiteKingCastle();

	boolean getWhiteQueenCastle();

	boolean getBlackKingCastle();

	boolean getBlackQueenCastle();

	void castle();

	void kingMove();

	void kingRookMove();

	void queenRookMove();
}
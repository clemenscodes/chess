package model.piece;

import model.bits.IBitboard;
import model.board.IBoard;

public interface IPiece {
	Pieces getVariant();

	IBitboard getBitboard();

	void setBitboard(IBitboard bitboard);

	IBitboard getAttacks(IBitboard piece, IBoard board);

	IBitboard getMoveMask(int source, int destination);

	boolean isInvalidMove(int source, int destination, IBoard board);
}

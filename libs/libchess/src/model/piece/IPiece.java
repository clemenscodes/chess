package model.piece;

import model.bits.IBitboard;
import model.board.IBoard;

public interface IPiece {
	Pieces getVariant();

	IBitboard getBitboard();

	IBitboard getMoveMask(int source, int destination);

	IBitboard getAttacks(IBitboard piece, IBoard board);

	IBitboard getAllAttacks(IBoard board);
}

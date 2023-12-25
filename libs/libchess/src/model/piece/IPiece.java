package model.piece;

import model.bits.IBitboard;

public interface IPiece {
	Pieces getVariant();

	IBitboard getBitboard();

	void setBitboard(IBitboard bitboard);
}

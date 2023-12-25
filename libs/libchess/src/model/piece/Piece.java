package model.piece;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;

public abstract class Piece implements IPiece, Movable, Serializable {

	private Pieces variant;
	private IBitboard bitboard;

	public Piece(Pieces variant) {
		setVariant(variant);
		setBitboard(new Bitboard());
	}

	public Pieces getVariant() {
		return variant;
	}

	public IBitboard getBitboard() {
		return bitboard;
	}

	public void setBitboard(IBitboard bitboard) {
		this.bitboard = bitboard;
	}

	private void setVariant(Pieces variant) {
		this.variant = variant;
	}
}

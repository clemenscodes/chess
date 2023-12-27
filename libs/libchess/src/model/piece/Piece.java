package model.piece;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.move.Moves;

public abstract class Piece implements IPiece, Serializable {

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

	private IBitboard getMoveMask(int source, int destination) {
		return Bitboard.merge(Bitboard.getSingleBit(source), Bitboard.getSingleBit(destination));
	}

	protected Moves determineMove(int source, int destination) {
		getBitboard().toggleBits(getMoveMask(source, destination));
		return Moves.Quiet;
	}
}

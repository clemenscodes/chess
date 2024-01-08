package model.piece;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;

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

	public IBitboard getMoveMask(int source, int destination) {
		return Bitboard.merge(Bitboard.getSingleBit(source), Bitboard.getSingleBit(destination));
	}

	public IBitboard getAllAttacks(IBoard board) {
		return getAttacks(getBitboard(), board);
	}

	public IBitboard removeFriendlyPieces(IBitboard piece, IBoard board) {
		return Bitboard.intersect(piece, Bitboard.negate(board.getFriendlyPieces()));
	}

	public boolean isInvalidMove(int source, int destination, IBoard board) {
		return !(
			Bitboard.checkBit(getBitboard(), source) &&
			Bitboard.checkBit(getAttacks(Bitboard.getSingleBit(source), board), destination)
		);
	}

	private void setVariant(Pieces variant) {
		this.variant = variant;
	}
}

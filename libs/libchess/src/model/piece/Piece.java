package model.piece;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;

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

	protected boolean isInvalidMove(int source, int destination, IBoard board) {
		if (!Bitboard.checkBit(getMovablePieces(board), source)) {
			System.err.println("Piece on " + Square.getSquare(source) + " can not move");
			return true;
		}
		if (!Bitboard.checkBit(getTargets(Bitboard.getSingleBit(source), board), destination)) {
			System.err.println("Can not move piece to " + Square.getSquare(destination));
			return true;
		}
		return false;
	}

	private void setVariant(Pieces variant) {
		this.variant = variant;
	}
}

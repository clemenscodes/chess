package model.piece.knight;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Movable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Knight extends Piece implements Movable, Serializable {

	public Knight(Pieces variant) {
		super(variant);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		IBitboard directions = Bitboard.mergeMany(
			new IBitboard[] {
				Bitboard.shiftEastEastNorth(piece),
				Bitboard.shiftEastEastSouth(piece),
				Bitboard.shiftWestWestNorth(piece),
				Bitboard.shiftWestWestSouth(piece),
				Bitboard.shiftNorthNorthEast(piece),
				Bitboard.shiftNorthNorthWest(piece),
				Bitboard.shiftSouthSouthEast(piece),
				Bitboard.shiftSouthSouthWest(piece),
			}
		);
		return removeFriendlyPieces(directions, board);
	}
}

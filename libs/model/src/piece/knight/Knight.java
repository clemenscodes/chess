package piece.knight;

import bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Pieces;
import model.piece.knight.IKnight;
import piece.Piece;

public abstract class Knight extends Piece implements IKnight {

	public Knight(Pieces variant) {
		super(variant);
	}

	public Knight(Pieces variant, IBitboard board) {
		super(variant, board);
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

	@Override
	public IBitboard getAllAttacks(IBoard board) {
		return getAttacks(getBitboard(), board);
	}
}

import api.model.bits.IBitboard;
import api.model.board.IBoard;
import api.model.piece.Pieces;
import api.model.piece.knight.IKnight;

abstract class Knight extends Piece implements IKnight {

	Knight(Pieces variant) {
		super(variant);
	}

	Knight(Pieces variant, IBitboard board) {
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

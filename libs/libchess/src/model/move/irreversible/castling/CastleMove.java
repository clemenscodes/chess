package model.move.irreversible.castling;

import static model.board.Square.*;

import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.IrreversibleMove;

public abstract class CastleMove extends IrreversibleMove {

	public static final Square whiteCastleSourceSquare = e1;
	public static final Square whiteKingCastleDestinationSquare = g1;
	public static final Square whiteRookKingCastleDestinationSquare = f1;
	public static final Square whiteQueenCastleDestinationSquare = c1;
	public static final Square whiteQueenCastleRookSquare = a1;
	public static final Square whiteQueenCastleRookDestinationSquare = d1;
	public static final Square whiteKingCastleRookSquare = h1;
	public static final Square blackCastleSourceSquare = e8;
	public static final Square blackKingCastleDestinationSquare = g8;
	public static final Square blackRookKingCastleDestinationSquare = f8;
	public static final Square blackQueenCastleDestinationSquare = c8;
	public static final Square blackQueenCastleRookDestinationSquare = d8;
	public static final Square blackKingCastleRookSquare = h8;
	public static final Square blackQueenCastleRookSquare = a8;

	public CastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		if (board.kingUnsafe()) {
			throw new Error("Can not castle when king is in check");
		}
		if (invalidCastle(board)) {
			throw new Error("Squares the king walks while castling must be safe and empty");
		}
		castle(source, destination, board);
	}

	protected boolean canCastleOverSquare(Square square, IBoard board) {
		return board.isSquareEmpty(square) && !board.isSquareAttacked(square);
	}

	protected boolean invalidCastle(IBoard board) {
		boolean validKingSquare = canCastleOverSquare(getCastledKingSquare(board), board);
		boolean validRookSquare = canCastleOverSquare(getCastledRookSquare(board), board);
		return !(validKingSquare && validRookSquare);
	}

	protected void castle(Square source, Square destination, IBoard board) {
		Square rookSquare = getRookSquare(board);
		IBitboard king = board.getPiece(source).getBitboard();
		IBitboard rook = board.getPiece(rookSquare).getBitboard();
		king.unsetBitByIndex(Square.getIndex(source));
		king.setBitByIndex(Square.getIndex(destination));
		rook.unsetBitByIndex(Square.getIndex(rookSquare));
		rook.setBitByIndex(Square.getIndex(getCastledRookSquare(board)));
		board.getFen().castle();
	}

	protected abstract Square getRookSquare(IBoard board);

	protected abstract Square getCastledKingSquare(IBoard board);

	protected abstract Square getCastledRookSquare(IBoard board);
}

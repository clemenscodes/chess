package model.move.irreversible.castling;

import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.IrreversibleMove;

public abstract class CastleMove extends IrreversibleMove {

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
		IBitboard king = board.getPiece(source).getBitboard();
		IBitboard rook = board.getPiece(destination).getBitboard();
		king.unsetBitByIndex(Square.getIndex(source));
		king.setBitByIndex(Square.getIndex(getCastledKingSquare(board)));
		rook.unsetBitByIndex(Square.getIndex(destination));
		rook.setBitByIndex(Square.getIndex(getCastledRookSquare(board)));
		board.getFen().castle();
	}

	protected abstract Square getCastledKingSquare(IBoard board);

	protected abstract Square getCastledRookSquare(IBoard board);
}

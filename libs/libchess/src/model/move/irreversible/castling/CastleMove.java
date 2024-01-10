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
			throw new Error("Squares between king and rook must be safe and empty");
		}
		board.getFen().castle();
		castle(source, destination, board);
	}

	protected boolean canCastleOverSquare(Square square, IBoard board) {
		return board.isSquareEmpty(square) && !board.isSquareAttacked(square);
	}

	protected IBitboard unsetKing(Square source, IBoard board) {
		IBitboard king = board.getPiece(source).getBitboard();
		king.unsetBitByIndex(Square.getIndex(source));
		return king;
	}

	protected IBitboard unsetRook(Square destination, IBoard board) {
		IBitboard rook = board.getPiece(destination).getBitboard();
		rook.unsetBitByIndex(Square.getIndex(destination));
		return rook;
	}

	protected abstract boolean invalidCastle(IBoard board);

	protected abstract void castle(Square source, Square destination, IBoard board);
}

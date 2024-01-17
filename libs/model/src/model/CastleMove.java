package model;

import static api.model.Square.*;

import api.model.IBitboard;
import api.model.IBoard;
import api.model.Square;

abstract class CastleMove extends IrreversibleMove {

	static final Square whiteCastleSourceSquare = e1;
	static final Square blackCastleSourceSquare = e8;

	CastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		if (board.kingUnsafe()) {
			throw new Error("Can not castle when king is in check");
		}
		if (invalidCastle(board)) {
			throw new Error("Squares the king walks while castling must be safe and empty");
		}
		castle(source, destination, board);
	}

	private boolean canCastleOverSquare(Square square, IBoard board) {
		return board.isSquareEmpty(square) && !board.isSquareAttacked(square);
	}

	private boolean invalidCastle(IBoard board) {
		boolean validKingSquare = canCastleOverSquare(getCastledKingSquare(board), board);
		boolean validRookSquare = canCastleOverSquare(getCastledRookSquare(board), board);
		return !(validKingSquare && validRookSquare);
	}

	private void castle(Square source, Square destination, IBoard board) {
		moveKing(source, destination, board);
		moveRook(board);
		removeCastlingRights(board);
	}

	private void moveKing(Square source, Square destination, IBoard board) {
		IBitboard king = board.getPiece(source).getBitboard();
		king.unsetBitByIndex(Board.getIndex(source));
		king.setBitByIndex(Board.getIndex(destination));
	}

	private void moveRook(IBoard board) {
		Square rookSquare = getRookSquare(board);
		IBitboard rook = board.getPiece(rookSquare).getBitboard();
		rook.unsetBitByIndex(Board.getIndex(rookSquare));
		rook.setBitByIndex(Board.getIndex(getCastledRookSquare(board)));
	}

	private void removeCastlingRights(IBoard board) {
		board.getFen().castle();
	}

	protected abstract Square getRookSquare(IBoard board);

	protected abstract Square getCastledKingSquare(IBoard board);

	protected abstract Square getCastledRookSquare(IBoard board);
}

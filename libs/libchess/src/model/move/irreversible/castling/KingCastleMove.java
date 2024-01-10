package model.move.irreversible.castling;

import static model.board.Square.*;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

public class KingCastleMove extends CastleMove {

	public KingCastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		if (!canCastleOverSquares(board)) {
			throw new Error("Squares between king and rook must be safe and empty");
		}
		IPiece king = board.getPiece(source);
		IPiece rook = board.getPiece(destination);
	}

	private boolean canCastleOverSquares(IBoard board) {
		boolean validBishopSquare = canCastleOverSquare(getBishopSquare(board), board);
		boolean validKnightSquare = canCastleOverSquare(getKnightSquare(board), board);
		return validBishopSquare && validKnightSquare;
	}

	private Square getBishopSquare(IBoard board) {
		return board.getFen().getActiveColor() == 'w' ? f1 : f8;
	}

	private Square getKnightSquare(IBoard board) {
		return board.getFen().getActiveColor() == 'w' ? g1 : g8;
	}
}

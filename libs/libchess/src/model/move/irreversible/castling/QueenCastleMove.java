package model.move.irreversible.castling;

import static model.board.Square.*;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

public class QueenCastleMove extends CastleMove {

	public QueenCastleMove(Square source, Square destination, IBoard board) {
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
		boolean validQueenSquare = canCastleOverSquare(getQueenSquare(board), board);
		return validBishopSquare && validKnightSquare && validQueenSquare;
	}

	private Square getBishopSquare(IBoard board) {
		return board.getFen().getActiveColor() == 'w' ? b1 : b8;
	}

	private Square getKnightSquare(IBoard board) {
		return board.getFen().getActiveColor() == 'w' ? c1 : c8;
	}

	private Square getQueenSquare(IBoard board) {
		return board.getFen().getActiveColor() == 'w' ? d1 : d8;
	}
}

package model.move.irreversible.castling;

import static model.board.Square.*;

import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;

public class KingCastleMove extends CastleMove {

	public KingCastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}

	protected void castle(Square source, Square destination, IBoard board) {
		IBitboard king = unsetKing(source, board);
		IBitboard rook = unsetRook(destination, board);
	}

	protected boolean invalidCastle(IBoard board) {
		boolean validBishopSquare = canCastleOverSquare(getBishopSquare(board), board);
		boolean validKnightSquare = canCastleOverSquare(getKnightSquare(board), board);
		return !(validBishopSquare && validKnightSquare);
	}

	private Square getBishopSquare(IBoard board) {
		return board.getFen().getActiveColor() == 'w' ? f1 : f8;
	}

	private Square getKnightSquare(IBoard board) {
		return board.getFen().getActiveColor() == 'w' ? g1 : g8;
	}
}

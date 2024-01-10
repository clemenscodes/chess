package model.move.irreversible.castling;

import static model.board.Square.*;

import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

public class QueenCastleMove extends CastleMove {

	public QueenCastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
	}

	protected void castle(Square source, Square destination, IBoard board) {
		IBitboard king = unsetKing(source, board);
		IBitboard rook = unsetRook(destination, board);
	}

	protected boolean invalidCastle(IBoard board) {
		boolean validBishopSquare = canCastleOverSquare(getBishopSquare(board), board);
		boolean validKnightSquare = canCastleOverSquare(getKnightSquare(board), board);
		boolean validQueenSquare = canCastleOverSquare(getQueenSquare(board), board);
		return !(validBishopSquare && validKnightSquare && validQueenSquare);
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

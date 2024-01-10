package model.move.irreversible.castling;

import static model.board.Square.*;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

public class QueenCastleMove extends CastleMove {

	public QueenCastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		IPiece king = board.getPiece(source);
		IPiece rook = board.getPiece(destination);
		// Squares between king and rook must be empty
		// Squares between king and rook must not be attacked by opponent
		switch (board.getFen().getActiveColor()) {
			case 'w' -> {
				Square knightSquare = b1;
				Square bishopSquare = c1;
				Square queenSquare = d1;
			}
			case 'b' -> {
				Square knightSquare = b8;
				Square bishopSquare = c8;
				Square queenSquare = d8;
			}
		}
	}
}

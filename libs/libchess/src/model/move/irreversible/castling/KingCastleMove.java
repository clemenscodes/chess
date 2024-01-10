package model.move.irreversible.castling;

import static model.board.Square.*;

import model.board.IBoard;
import model.board.Square;
import model.fen.IForsythEdwardsNotation;
import model.piece.IPiece;

public class KingCastleMove extends CastleMove {

	public KingCastleMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		IPiece king = board.getPiece(source);
		IPiece rook = board.getPiece(destination);
		// Squares between king and rook must be empty
		// Squares between king and rook must not be attacked by opponent
		switch (board.getFen().getActiveColor()) {
			case 'w' -> {
				Square bishopSquare = f1;
				Square knightSquare = g1;
			}
			case 'b' -> {
				Square bishopSquare = f8;
				Square knightSquare = g8;
			}
		}
	}
}

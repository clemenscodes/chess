package model.piece.bishop;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.move.Move;
import model.move.reversible.QuietMove;
import model.piece.Movable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Bishop extends Piece implements Movable, Serializable {

	public Bishop(Pieces variant) {
		super(variant);
	}

	public boolean isInvalidMove(int source, int destination, IBoard board) {
		return false;
	}

	public Move move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		return new QuietMove(Square.getSquare(source), Square.getSquare(destination), board, this);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		return new Bitboard();
	}
}

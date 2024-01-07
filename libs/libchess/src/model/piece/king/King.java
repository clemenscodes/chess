package model.piece.king;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.move.IMove;
import model.piece.Movable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class King extends Piece implements Movable, Serializable {

	public King(Pieces variant) {
		super(variant);
	}

	public boolean isInvalidMove(int source, int destination, IBoard board) {
		return false;
	}

	public IMove move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		return new QuietMove(Square.getSquare(source), Square.getSquare(destination), board, this);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		return new Bitboard();
	}
}

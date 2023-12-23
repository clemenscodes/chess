package model.piece.rook;

import java.io.Serializable;
import model.board.IBoard;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Rook extends Piece implements Serializable {

	public Rook(Pieces variant) {
		super(variant);
	}

	public boolean isInvalidMove(int source, int destination, IBoard board) {
		return false;
	}

	public IBoard move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		return board;
	}
}

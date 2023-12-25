package model.piece.king;

import java.io.Serializable;
import model.board.IBoard;
import model.move.IMove;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class King extends Piece implements Serializable {

	public King(Pieces variant) {
		super(variant);
	}

	public boolean isInvalidMove(int source, int destination, IBoard board) {
		return false;
	}

	public void move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
	}

	public IMove[] generateMoves(IBoard board) {
		return new IMove[0];
	}
}

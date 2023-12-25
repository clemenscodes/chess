package model.piece.queen;

import java.io.Serializable;
import model.board.IBoard;
import model.move.IMove;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Queen extends Piece implements Serializable {

	public Queen(Pieces variant) {
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

	public IMove[] generateMoves() {
		return new IMove[0];
	}
}

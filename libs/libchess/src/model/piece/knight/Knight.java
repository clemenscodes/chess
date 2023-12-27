package model.piece.knight;

import java.io.Serializable;
import model.board.IBoard;
import model.move.IMove;
import model.piece.Movable;
import model.piece.Piece;
import model.piece.Pieces;
import model.util.io.reader.IReader;

public abstract class Knight extends Piece implements Movable, Serializable {

	public Knight(Pieces variant) {
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

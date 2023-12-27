package model.piece.king;

import java.io.Serializable;
import model.board.IBoard;
import model.move.IMove;
import model.move.Moves;
import model.piece.Movable;
import model.piece.Piece;
import model.piece.Pieces;
import model.util.io.reader.IReader;

public abstract class King extends Piece implements Movable, Serializable {

	public King(Pieces variant) {
		super(variant);
	}

	public boolean isInvalidMove(int source, int destination, IBoard board) {
		return false;
	}

	public Moves move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}

		return Moves.Quiet;
	}

	public IMove[] generateMoves(IBoard board) {
		return new IMove[0];
	}
}

package model.piece.knight;

import java.io.Serializable;
import model.board.Board;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Knight extends Piece implements Serializable {

	public Knight(Pieces variant) {
		super(variant);
	}

	public boolean isValidMove(int source, int destination, Board board) {
		return false;
	}
}

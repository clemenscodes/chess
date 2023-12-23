package model.piece.king;

import java.io.Serializable;
import model.board.Board;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class King extends Piece implements Serializable {

	public King(Pieces variant) {
		super(variant);
	}

	public boolean isValidMove(int source, int destination, Board board) {
		return false;
	}
}

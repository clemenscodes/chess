package model.piece.bishop;

import java.io.Serializable;
import model.board.Board;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Bishop extends Piece implements Serializable {

	public Bishop(Pieces variant) {
		super(variant);
	}

	public boolean isValidMove(int source, int destination, Board board) {
		return false;
	}
}

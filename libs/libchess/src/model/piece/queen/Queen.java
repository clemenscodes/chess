package model.piece.queen;

import java.io.Serializable;
import model.board.Board;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Queen extends Piece implements Serializable {

	public Queen(Pieces variant) {
		super(variant);
	}

	public boolean isValidMove(int source, int destination, Board board) {
		return false;
	}
}

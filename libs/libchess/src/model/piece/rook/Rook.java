package model.piece.rook;

import java.io.Serializable;
import model.Board;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Rook extends Piece implements Serializable {

	public Rook(Pieces variant, char symbol) {
		super(variant, symbol);
	}

	public boolean isValidMove(int source, int destination, Board board) {
		return false;
	}
}

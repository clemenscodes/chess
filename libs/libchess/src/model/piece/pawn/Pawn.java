package model.piece.pawn;

import java.io.Serializable;
import model.Board;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Pawn extends Piece implements Serializable {

	public Pawn(Pieces variant, char symbol) {
		super(variant, symbol);
	}

	public boolean isValidMove(int source, int destination, Board board) {
		return false;
	}
}

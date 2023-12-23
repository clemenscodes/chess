package model.piece.pawn.extension;

import java.io.Serializable;
import model.board.Board;
import model.piece.Pieces;
import model.piece.pawn.Pawn;

public class WhitePawn extends Pawn implements Serializable {

	public static final char SYMBOL = '♙';

	public WhitePawn() {
		super(Pieces.WhitePawn);
	}

	public boolean isValidMove(int source, int destination, Board board) {
		return false;
	}
}

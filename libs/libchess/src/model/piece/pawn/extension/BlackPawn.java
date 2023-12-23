package model.piece.pawn.extension;

import java.io.Serializable;
import model.Board;
import model.piece.Pieces;
import model.piece.pawn.Pawn;

public class BlackPawn extends Pawn implements Serializable {

	public static final char SYMBOL = '♟';

	public BlackPawn() {
		super(Pieces.BlackPawn);
	}

	public boolean isValidMove(int source, int destination, Board board) {
		return false;
	}
}

package model.piece.pawn.extension;

import java.io.Serializable;
import model.board.IBoard;
import model.move.IMove;
import model.piece.Pieces;
import model.piece.pawn.Pawn;

public class BlackPawn extends Pawn implements Serializable {

	public static final char SYMBOL = '♟';

	public BlackPawn() {
		super(Pieces.BlackPawn);
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

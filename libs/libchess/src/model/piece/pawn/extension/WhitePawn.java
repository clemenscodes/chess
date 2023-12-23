package model.piece.pawn.extension;

import java.io.Serializable;
import model.board.IBoard;
import model.board.Square;
import model.piece.Pieces;
import model.piece.pawn.Pawn;

public class WhitePawn extends Pawn implements Serializable {

	public static final char SYMBOL = '♙';

	public WhitePawn() {
		super(Pieces.WhitePawn);
	}

	public boolean isInvalidMove(int source, int destination, IBoard board) {
		return false;
	}

	public IBoard move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		System.out.print("Moving white pawn from ");
		System.out.print(Square.getSquare(source));
		System.out.print(" to ");
		System.out.println(Square.getSquare(destination));
		return board;
	}
}

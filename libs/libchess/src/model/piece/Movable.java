package model.piece;

import model.board.IBoard;
import model.move.IMove;

public interface Movable {
	IBoard move(int source, int destination, IBoard board);
	boolean isInvalidMove(int source, int destination, IBoard board);
	IMove[] generateMoves();
}

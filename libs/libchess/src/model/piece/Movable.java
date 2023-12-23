package model.piece;

import model.board.Board;

public interface Movable {
	boolean isValidMove(int source, int destination, Board board);
}

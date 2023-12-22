package model.piece;

import model.Board;

public interface Movable {
	boolean isValidMove(int source, int destination, Board board);
}

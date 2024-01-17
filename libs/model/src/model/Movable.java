package model;

public interface Movable {
	IMove move(int source, int destination, IBoard board);
}

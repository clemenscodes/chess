package model;

interface Movable {
	IMove move(int source, int destination, IBoard board);
}

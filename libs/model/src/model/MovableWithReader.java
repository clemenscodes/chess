package model;

interface MovableWithReader<T> {
	IMove move(int source, int destination, IBoard board, IReader<T> reader);
}

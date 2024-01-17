package api.model;

public interface MovableWithReader {
	IMove move(int source, int destination, IBoard board, IReader reader);
}

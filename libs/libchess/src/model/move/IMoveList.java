package model.move;

import model.Square;

public interface IMoveList {
	void move(Square source, Square destination);

	void unmove();

	int getPlayedMoves();

	IMove[] getMoves();
}

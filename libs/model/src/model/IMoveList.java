package model;

import api.model.Square;

interface IMoveList {
	void makeMove(api.model.Square source, Square destination, IBoard board, IReader reader);

	int getPlayedMoves();
}
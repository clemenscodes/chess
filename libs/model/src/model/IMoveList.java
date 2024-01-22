package model;

import api.model.Square;
import api.model.State;

interface IMoveList {
	void makeMove(
		api.model.Square source,
		Square destination,
		IBoard board,
		IReader<String> reader,
		IWriter<State> writer
	);

	int getPlayedMoves();
}

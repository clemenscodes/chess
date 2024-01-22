package model;

import api.model.State;

interface MovableWithReader {
	IMove move(
		int source,
		int destination,
		IBoard board,
		IReader<String> reader,
		IWriter<State> writer
	);
}

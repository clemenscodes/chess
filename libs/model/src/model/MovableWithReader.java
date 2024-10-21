package model;

import api.State;

interface MovableWithReader {
    IMove move(
            int source,
            int destination,
            IBoard board,
            IReader<String> reader,
            IWriter<State> writer);
}

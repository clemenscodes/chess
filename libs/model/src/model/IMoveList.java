package model;

import api.Square;
import api.State;

interface IMoveList {
    void makeMove(
            Square source,
            Square destination,
            IBoard board,
            IReader<String> reader,
            IWriter<State> writer);

    int getPlayedMoves();
}

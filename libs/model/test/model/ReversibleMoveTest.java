package model;

import static api.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import api.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReversibleMoveTest {

    static class ReversibleMoveMock extends ReversibleMove {

        public ReversibleMoveMock(Square source, Square destination, IBoard board) {
            super(source, destination, board);
        }
    }

    private IBoard board;

    @BeforeEach
    void init() {
        board = new Board();
    }

    @Test
    void shouldIncrementHalfMoveClock() {
        int halfMoveClock = board.getFen().getHalfMoveClock();
        assertEquals(0, halfMoveClock);
        new ReversibleMoveMock(e2, e4, board);
        halfMoveClock = board.getFen().getHalfMoveClock();
        assertEquals(1, halfMoveClock);
    }
}

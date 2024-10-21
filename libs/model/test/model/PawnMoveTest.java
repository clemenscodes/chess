package model;

import static api.Square.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnMoveTest {

    private IBoard board;

    @BeforeEach
    void init() {
        board = new Board();
    }

    @Test
    void shouldUnsetPawnBit() {
        IPiece piece = board.getWhitePawn();
        new SinglePawnPushMove(e2, e3, board, piece);
        board.getFen().updatePiecePlacementData(board);
        String expected = "rnbqkbnr/pppppppp/8/8/8/4P3/PPPP1PPP/RNBQKBNR w KQkq - 0 1";
        assertEquals(expected, board.getFen().toString());
    }
}

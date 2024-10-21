package model;

import static api.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnCaptureMoveTest {

    private IBoard board;
    private IPiece piece;

    @BeforeEach
    void init() {
        String fen = "rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2";
        board = new Board(new ForsythEdwardsNotation(fen));
        piece = board.getWhitePawn();
    }

    @Test
    void shouldSetPawnBit() {
        String expected = """
                00000000
                00000000
                00000000
                00000000
                00001000
                00000000
                11110111
                00000000""";
        assertEquals(expected, piece.getBitboard().toString());
        new PawnCaptureMove(e4, d5, board, piece);
        expected = """
                00000000
                00000000
                00000000
                00010000
                00000000
                00000000
                11110111
                00000000""";
        assertEquals(expected, piece.getBitboard().toString());
    }

    @Test
    void shouldPrint() {
        PawnCaptureMove move = new PawnCaptureMove(e4, d5, board, piece);
        assertEquals("e4d5", move.toString());
    }
}

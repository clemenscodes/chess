package model;

import static org.junit.jupiter.api.Assertions.*;

import api.Square;
import org.junit.jupiter.api.Test;

public class SquareTest {

    @Test
    void testGetSquare() {
        for (int i = 0; i < 64; i++) {
            Square square = Board.getSquare(i);
            assertNotNull(square);
            assertEquals(i, Board.getIndex(square));
        }
    }

    @Test
    void testGetIndex() {
        for (int i = 0; i < 64; i++) {
            Square square = Board.getSquare(i);
            assertEquals(i, Board.getIndex(square));
        }
    }

    @Test
    void testGetSquareInvalidIndex() {
        assertThrows(Error.class, () -> Board.getSquare(-1));
        assertThrows(Error.class, () -> Board.getSquare(64));
    }

    @Test
    void testGetIndexInvalidSquare() {
        assertThrows(Error.class, () -> Board.getIndex(null));
        assertThrows(
                IllegalArgumentException.class,
                () -> Board.getIndex(Square.valueOf("invalid")));
    }
}

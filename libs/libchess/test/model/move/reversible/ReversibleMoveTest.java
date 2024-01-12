package model.move.reversible;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import model.board.Board;
import model.board.IBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReversibleMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		board = new Board();
	}

	@Test
	void shouldIncrementHalfMoveClock() {
		int halfMoveClock = board.getFen().getHalfMoveClock();
		assertEquals(0, halfMoveClock);
		new QuietMove(e2, e4, board);
		halfMoveClock = board.getFen().getHalfMoveClock();
		assertEquals(1, halfMoveClock);
	}
}

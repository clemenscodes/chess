import static api.model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import api.model.board.IBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IrreversibleMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		board = new Board();
	}

	@Test
	void shouldResetHalfMoveClock() {
		String fen = "rnbqkb1r/ppp1pppp/5n2/3p4/8/2N2N2/PPPPPPPP/R1BQKB1R w KQkq - 2 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		int halfMoveClock = board.getFen().getHalfMoveClock();
		assertEquals(2, halfMoveClock);
		new CaptureMove(c3, d5, board);
		halfMoveClock = board.getFen().getHalfMoveClock();
		assertEquals(0, halfMoveClock);
	}
}

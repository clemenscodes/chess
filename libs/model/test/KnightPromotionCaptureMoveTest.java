import static api.model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import api.model.board.IBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KnightPromotionCaptureMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		String fen = "rnbq1bnr/pppkpPpp/8/8/8/3p4/PPPP1PPP/RNBQKBNR w KQ - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
	}

	@Test
	void shouldCaptureAndPromotePawnToKnight() {
		new KnightPromotionCaptureMove(f7, g8, board);
		board.getFen().updatePiecePlacementData(board);
		board.getFen().switchActiveColor();
		String expected = "rnbq1bNr/pppkp1pp/8/8/8/3p4/PPPP1PPP/RNBQKBNR b KQ - 0 5";
		assertEquals(expected, board.getFen().toString());
	}

	@Test
	void shouldPrint() {
		var move = new KnightPromotionCaptureMove(f7, g8, board);
		assertEquals("f7g8", move.toString());
	}
}

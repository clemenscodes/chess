package model.move.irreversible.pawn.promotion.capturing;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import model.board.Board;
import model.board.IBoard;
import model.fen.ForsythEdwardsNotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueenPromotionCaptureMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		String fen = "rnbq1bnr/pppkpPpp/8/8/8/3p4/PPPP1PPP/RNBQKBNR w KQ - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
	}

	@Test
	void shouldCaptureAndPromotePawnToQueen() {
		new QueenPromotionCaptureMove(f7, g8, board);
		board.getFen().switchActiveColor();
		board.getFen().updatePiecePlacementData(board);
		String expected = "rnbq1bQr/pppkp1pp/8/8/8/3p4/PPPP1PPP/RNBQKBNR b KQ - 0 5";
		assertEquals(expected, board.getFen().toString());
	}

	@Test
	void shouldPrint() {
		var move = new QueenPromotionCaptureMove(f7, g8, board);
		assertEquals("f7g8", move.toString());
	}
}

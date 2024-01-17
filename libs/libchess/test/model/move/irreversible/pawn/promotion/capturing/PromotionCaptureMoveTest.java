package model.move.irreversible.pawn.promotion.capturing;

import static model.board.Square.*;

import model.board.Board;
import model.board.IBoard;
import model.fen.ForsythEdwardsNotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PromotionCaptureMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		String fen = "rnbq1bnr/pppkpPpp/8/8/8/3p4/PPPP1PPP/RNBQKBNR w KQ - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
	}

	@Test
	void shouldCaptureAndPromoteWhitePawn() {
		new QueenPromotionCaptureMove(f7, g8, board);
		board.getFen().updatePiecePlacementData(board);
		board.getFen().switchActiveColor();
		String expected = "rnbq1bQr/pppkp1pp/8/8/8/3p4/PPPP1PPP/RNBQKBNR b KQ - 0 5";
		assertEquals(expected, board.getFen().toString());
	}

	@Test
	void shouldCaptureAndPromoteBlackPawn() {
		String fen = "rnbqkbnr/ppPp1ppp/8/8/8/8/PPPKPpPP/RNBQ1BNR b kq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		new QueenPromotionCaptureMove(f2, g1, board);
		board.getFen().switchActiveColor();
		board.getFen().updatePiecePlacementData(board);
		String expected = "rnbqkbnr/ppPp1ppp/8/8/8/8/PPPKP1PP/RNBQ1BqR w kq - 0 6";
		System.out.println(board);
		assertEquals(expected, board.getFen().toString());
	}
}

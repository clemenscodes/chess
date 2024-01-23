package model;

import static api.Square.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PromotionMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		String fen = "rnbq3r/pppkbPpp/4pn2/8/8/1PNp1Q2/P1PP1PPP/R1B1KBNR w KQ - 1 8";
		board = new Board(new ForsythEdwardsNotation(fen));
	}

	@Test
	void shouldPromoteWhitePawn() {
		new QueenPromotionMove(f7, f8, board);
		board.getFen().switchActiveColor();
		board.getFen().updatePiecePlacementData(board);
		String expected = "rnbq1Q1r/pppkb1pp/4pn2/8/8/1PNp1Q2/P1PP1PPP/R1B1KBNR b KQ - 0 8";
		assertEquals(expected, board.getFen().toString());
	}

	@Test
	void shouldPromoteBlackPawn() {
		String fen = "rnb1kb1r/ppqp1ppp/5n2/8/2B1P3/8/PPPK1pPP/RNBQ2NR b kq - 2 7";
		board = new Board(new ForsythEdwardsNotation(fen));
		new QueenPromotionMove(f2, f1, board);
		board.getFen().switchActiveColor();
		board.getFen().updatePiecePlacementData(board);
		String expected = "rnb1kb1r/ppqp1ppp/5n2/8/2B1P3/8/PPPK2PP/RNBQ1qNR w kq - 0 8";
		assertEquals(expected, board.getFen().toString());
	}
}

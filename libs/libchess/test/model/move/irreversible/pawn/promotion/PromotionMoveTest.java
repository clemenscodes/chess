package model.move.irreversible.pawn.promotion;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import model.board.Board;
import model.board.IBoard;
import model.fen.ForsythEdwardsNotation;
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
	void shouldPromotePawn() {
		new QueenPromotionMove(f7, f8, board);
		board.getFen().switchActiveColor();
		board.getFen().updatePiecePlacementData(board);
		String expected = "rnbq1Q1r/pppkb1pp/4pn2/8/8/1PNp1Q2/P1PP1PPP/R1B1KBNR b KQ - 0 8";
		assertEquals(expected, board.getFen().toString());
	}
}

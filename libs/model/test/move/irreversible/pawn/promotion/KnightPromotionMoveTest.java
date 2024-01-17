package model.move.irreversible.pawn.promotion;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import model.board.Board;
import model.board.IBoard;
import model.fen.ForsythEdwardsNotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KnightPromotionMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		String fen = "rnbq3r/pppkbPpp/4pn2/8/8/1PNp1Q2/P1PP1PPP/R1B1KBNR w KQ - 1 8";
		board = new Board(new ForsythEdwardsNotation(fen));
	}

	@Test
	void shouldPromotePawnToKnight() {
		new KnightPromotionMove(f7, f8, board);
		board.getFen().switchActiveColor();
		board.getFen().updatePiecePlacementData(board);
		String expected = "rnbq1N1r/pppkb1pp/4pn2/8/8/1PNp1Q2/P1PP1PPP/R1B1KBNR b KQ - 0 8";
		assertEquals(expected, board.getFen().toString());
	}

	@Test
	void shouldPrint() {
		var move = new KnightPromotionMove(f7, f8, board);
		assertEquals("f7f8", move.toString());
	}
}

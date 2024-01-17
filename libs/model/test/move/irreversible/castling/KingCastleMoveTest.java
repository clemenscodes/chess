package model.move.irreversible.castling;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import model.board.Board;
import model.board.IBoard;
import model.fen.ForsythEdwardsNotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KingCastleMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		board = new Board();
	}

	@Test
	void shouldKingCastleWhite() {
		String fen = "r1bqk2r/pppp1ppp/2n2n2/1Bb1p3/4P3/3P1N2/PPP2PPP/RNBQK2R w KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		new KingCastleMove(e1, g1, board);
		board.getFen().switchActiveColor();
		board.getFen().updatePiecePlacementData(board);
		String expected = "r1bqk2r/pppp1ppp/2n2n2/1Bb1p3/4P3/3P1N2/PPP2PPP/RNBQ1RK1 b kq - 2 5";
		assertEquals(expected, board.getFen().toString());
	}

	@Test
	void shouldKingCastleBlack() {
		String fen = "r1bqk2r/pppp1ppp/2n2n2/1Bb1p3/4P3/2N2N2/PPPP1PPP/R1BQ1RK1 b kq - 7 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		new KingCastleMove(e8, g8, board);
		board.getFen().switchActiveColor();
		board.getFen().updatePiecePlacementData(board);
		String expected = "r1bq1rk1/pppp1ppp/2n2n2/1Bb1p3/4P3/2N2N2/PPPP1PPP/R1BQ1RK1 w - - 8 6";
		assertEquals(expected, board.getFen().toString());
	}

	@Test
	void shouldPrint() {
		String fen = "r1bqk2r/pppp1ppp/2n2n2/1Bb1p3/4P3/3P1N2/PPP2PPP/RNBQK2R w KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		KingCastleMove move = new KingCastleMove(e1, g1, board);
		assertEquals("O-O", move.toString());
	}
}

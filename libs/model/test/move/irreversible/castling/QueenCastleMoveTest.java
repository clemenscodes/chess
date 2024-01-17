package model.move.irreversible.castling;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import model.board.Board;
import model.board.IBoard;
import model.fen.ForsythEdwardsNotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueenCastleMoveTest {

	private IBoard board;

	@BeforeEach
	void init() {
		board = new Board();
	}

	@Test
	void shouldQueenCastleWhite() {
		String fen = "r3kbnr/pppqpppp/2npb3/8/8/2NPB3/PPPQPPPP/R3KBNR w KQkq - 4 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		new QueenCastleMove(e1, c1, board);
		board.getFen().switchActiveColor();
		board.getFen().updatePiecePlacementData(board);
		String expected = "r3kbnr/pppqpppp/2npb3/8/8/2NPB3/PPPQPPPP/2KR1BNR b kq - 5 5";
		assertEquals(expected, board.getFen().toString());
	}

	@Test
	void shouldQueenCastleBlack() {
		String fen = "r3kbnr/pppqpppp/2npb3/8/8/2NPB3/PPPQPPPP/2KR1BNR b kq - 5 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		new QueenCastleMove(e8, c8, board);
		board.getFen().switchActiveColor();
		board.getFen().updatePiecePlacementData(board);
		String expected = "2kr1bnr/pppqpppp/2npb3/8/8/2NPB3/PPPQPPPP/2KR1BNR w - - 6 6";
		assertEquals(expected, board.getFen().toString());
	}

	@Test
	void shouldPrint() {
		String fen = "r3kbnr/pppqpppp/2npb3/8/8/2NPB3/PPPQPPPP/R3KBNR w KQkq - 4 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		QueenCastleMove move = new QueenCastleMove(e1, c1, board);
		assertEquals("O-O-O", move.toString());
	}
}

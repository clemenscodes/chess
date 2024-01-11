package model.move;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import model.bits.Bitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.fen.ForsythEdwardsNotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveTest {

	private IBoard board;

	@BeforeEach
	void initBoard() {
		board = new Board();
	}

	@Test
	void shouldDeterminePromotion() {
		boolean result = Move.isPromotion(Bitboard.merge(Board.firstRank, Board.eighthRank));
		assertTrue(result);
	}

	@Test
	void shouldDetermineCapture() {
		boolean result = Move.isCapture(Bitboard.getSingleBit(Square.getIndex(e2)), board);
		assertTrue(result);
	}

	@Test
	void shouldDetermineEnPassant() {
		board =
			new Board(
				new ForsythEdwardsNotation(
					"rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"
				)
			);
		boolean result = Move.isEnPassant(Bitboard.getSingleBit(Square.getIndex(e3)), board);
		assertTrue(result);
	}
}

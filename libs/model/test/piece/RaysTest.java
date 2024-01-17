package piece;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import bits.Bitboard;
import board.Board;
import fen.ForsythEdwardsNotation;
import model.bits.IBitboard;
import model.board.IBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RaysTest {

	private IBoard board;
	private IBitboard slider;

	@BeforeEach
	void setup() {
		board = new Board(new ForsythEdwardsNotation("8/8/8/8/8/8/8/8 w - - 0 1"));
		slider = Bitboard.getSingleBit(Board.getIndex(h8));
	}

	@Test
	void shouldReturnNoRayIfNoPieceGiven() {
		IBitboard rays = Bitboard.getDiagonalRays(new Bitboard(), board);
		assertEquals(0, rays.getBits());
	}

	@Test
	void shouldGetDiagonalRaysFromTopRightOnEmptyBoard() {
		IBitboard rays = Bitboard.getDiagonalRays(slider, board);
		String expected =
			"""
			00000000
			00000010
			00000100
			00001000
			00010000
			00100000
			01000000
			10000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetDiagonalRaysFromTopLeftOnEmptyBoard() {
		slider = Bitboard.getSingleBit(Board.getIndex(a8));
		IBitboard rays = Bitboard.getDiagonalRays(slider, board);
		String expected =
			"""
			00000000
			01000000
			00100000
			00010000
			00001000
			00000100
			00000010
			00000001""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetDiagonalRaysFromBottomLeftOnEmptyBoard() {
		slider = Bitboard.getSingleBit(Board.getIndex(a1));
		IBitboard rays = Bitboard.getDiagonalRays(slider, board);
		String expected =
			"""
			00000001
			00000010
			00000100
			00001000
			00010000
			00100000
			01000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetDiagonalRaysFromBottomRightOnEmptyBoard() {
		slider = Bitboard.getSingleBit(Board.getIndex(h1));
		IBitboard rays = Bitboard.getDiagonalRays(slider, board);
		String expected =
			"""
			10000000
			01000000
			00100000
			00010000
			00001000
			00000100
			00000010
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetDiagonalRaysFromCenterOnEmptyBoard() {
		slider = Bitboard.getSingleBit(Board.getIndex(e4));
		IBitboard rays = Bitboard.getDiagonalRays(slider, board);
		String expected =
			"""
			10000000
			01000001
			00100010
			00010100
			00000000
			00010100
			00100010
			01000001""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetDiagonalRaysFromCenterAsWhite() {
		board = new Board();
		slider = Bitboard.getSingleBit(Board.getIndex(e4));
		IBitboard rays = Bitboard.getDiagonalRays(slider, board);
		String expected =
			"""
			00000000
			01000001
			00100010
			00010100
			00000000
			00010100
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetDiagonalRaysFromTopRightAsWhite() {
		board = new Board();
		IBitboard rays = Bitboard.getDiagonalRays(slider, board);
		String expected =
			"""
			00000000
			00000010
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetDiagonalRaysFromTopLeftAsWhite() {
		board = new Board();
		slider = Bitboard.getSingleBit(Board.getIndex(a8));
		IBitboard rays = Bitboard.getDiagonalRays(slider, board);
		String expected =
			"""
			00000000
			01000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetDiagonalRaysFromBottomLeftAsWhite() {
		board = new Board();
		slider = Bitboard.getSingleBit(Board.getIndex(a1));
		IBitboard rays = Bitboard.getDiagonalRays(slider, board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetDiagonalRaysFromBottomRightAsWhite() {
		board = new Board();
		slider = Bitboard.getSingleBit(Board.getIndex(h1));
		IBitboard rays = Bitboard.getDiagonalRays(slider, board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetVerticalRaysFromTopRightOnEmptyBoard() {
		IBitboard rays = Bitboard.getVerticalRays(slider, board);
		String expected =
			"""
			00000000
			00000001
			00000001
			00000001
			00000001
			00000001
			00000001
			00000001""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetVerticalRaysFromTopLeftOnEmptyBoard() {
		slider = Bitboard.getSingleBit(Board.getIndex(a8));
		IBitboard rays = Bitboard.getVerticalRays(slider, board);
		String expected =
			"""
			00000000
			10000000
			10000000
			10000000
			10000000
			10000000
			10000000
			10000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetVerticalRaysFromBottomLeftOnEmptyBoard() {
		slider = Bitboard.getSingleBit(Board.getIndex(a1));
		IBitboard rays = Bitboard.getVerticalRays(slider, board);
		String expected =
			"""
			10000000
			10000000
			10000000
			10000000
			10000000
			10000000
			10000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetVerticalRaysFromBottomRightOnEmptyBoard() {
		slider = Bitboard.getSingleBit(Board.getIndex(h1));
		IBitboard rays = Bitboard.getVerticalRays(slider, board);
		String expected =
			"""
			00000001
			00000001
			00000001
			00000001
			00000001
			00000001
			00000001
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetVerticalRaysFromCenterOnEmptyBoard() {
		slider = Bitboard.getSingleBit(Board.getIndex(e4));
		IBitboard rays = Bitboard.getVerticalRays(slider, board);
		String expected =
			"""
			00001000
			00001000
			00001000
			00001000
			00000000
			00001000
			00001000
			00001000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetVerticalRaysFromCenterAsWhite() {
		board = new Board();
		slider = Bitboard.getSingleBit(Board.getIndex(e4));
		IBitboard rays = Bitboard.getVerticalRays(slider, board);
		String expected =
			"""
			00000000
			00001000
			00001000
			00001000
			00000000
			00001000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetVerticalRaysFromTopRightAsWhite() {
		board = new Board();
		IBitboard rays = Bitboard.getVerticalRays(slider, board);
		String expected =
			"""
			00000000
			00000001
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetVerticalRaysFromTopLeftAsWhite() {
		board = new Board();
		slider = Bitboard.getSingleBit(Board.getIndex(a8));
		IBitboard rays = Bitboard.getVerticalRays(slider, board);
		String expected =
			"""
			00000000
			10000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetVerticalRaysFromBottomLeftAsWhite() {
		board = new Board();
		slider = Bitboard.getSingleBit(Board.getIndex(a1));
		IBitboard rays = Bitboard.getVerticalRays(slider, board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetVerticalRaysFromBottomRightAsWhite() {
		board = new Board();
		slider = Bitboard.getSingleBit(Board.getIndex(h1));
		IBitboard rays = Bitboard.getVerticalRays(slider, board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetHorizontalRaysFromTopRightOnEmptyBoard() {
		IBitboard rays = Bitboard.getHorizontalRays(slider, board);
		String expected =
			"""
			11111110
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetHorizontalRaysFromTopLeftOnEmptyBoard() {
		slider = Bitboard.getSingleBit(Board.getIndex(a8));
		IBitboard rays = Bitboard.getHorizontalRays(slider, board);
		String expected =
			"""
			01111111
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetHorizontalRaysFromBottomLeftOnEmptyBoard() {
		slider = Bitboard.getSingleBit(Board.getIndex(a1));
		IBitboard rays = Bitboard.getHorizontalRays(slider, board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			01111111""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetHorizontalRaysFromBottomRightOnEmptyBoard() {
		slider = Bitboard.getSingleBit(Board.getIndex(h1));
		IBitboard rays = Bitboard.getHorizontalRays(slider, board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			11111110""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetHorizontalRaysFromCenterOnEmptyBoard() {
		slider = Bitboard.getSingleBit(Board.getIndex(e4));
		IBitboard rays = Bitboard.getHorizontalRays(slider, board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			11110111
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetHorizontalRaysFromCenterAsWhite() {
		board = new Board();
		slider = Bitboard.getSingleBit(Board.getIndex(e4));
		IBitboard rays = Bitboard.getHorizontalRays(slider, board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			11110111
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetHorizontalRaysFromTopRightAsWhite() {
		board = new Board();
		IBitboard rays = Bitboard.getHorizontalRays(slider, board);
		String expected =
			"""
			00000010
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetHorizontalRaysFromTopLeftAsWhite() {
		board = new Board();
		slider = Bitboard.getSingleBit(Board.getIndex(a8));
		IBitboard rays = Bitboard.getHorizontalRays(slider, board);
		String expected =
			"""
			01000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetHorizontalRaysFromBottomLeftAsWhite() {
		board = new Board();
		slider = Bitboard.getSingleBit(Board.getIndex(a1));
		IBitboard rays = Bitboard.getHorizontalRays(slider, board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}

	@Test
	void shouldGetHorizontalRaysFromBottomRightAsWhite() {
		board = new Board();
		slider = Bitboard.getSingleBit(Board.getIndex(h1));
		IBitboard rays = Bitboard.getHorizontalRays(slider, board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, rays.toString());
	}
}

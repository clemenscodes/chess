package model.piece.pawn;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.fen.ForsythEdwardsNotation;
import model.reader.IReader;
import model.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WhitePawnTest {

	private WhitePawn piece;
	private IReader reader;
	private IBoard board;

	@BeforeEach
	void setup() {
		String inputString = "Q";
		byte[] bytes = inputString.getBytes(StandardCharsets.UTF_8);
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		board = new Board(new ForsythEdwardsNotation("8/8/8/8/8/8/8/8 w - - 0 1"));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
	}

	@Test
	void shouldNotGetAttacksWithoutEnemy() {
		IBitboard attacks = piece.getAttacks(Bitboard.getSingleBit(Square.getIndex(e2)), board);
		assertEquals(0, attacks.getBits());
	}

	@Test
	void shouldGetAttacksWithEnemy() {
		String fen = "rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		IBitboard attacks = piece.getAttacks(Bitboard.getSingleBit(Square.getIndex(e4)), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00010000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksWithEnemies() {
		String fen = "rnbqkbnr/ppp1p1pp/8/3p1p2/3PP3/8/PPP2PPP/RNBQKBNR w KQkq f6 0 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		IBitboard attacks = piece.getAttacks(Bitboard.getSingleBit(Square.getIndex(e4)), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00010100
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetEnPassantAttack() {
		String fen = "rnb1kbnr/ppp3pp/3qp3/3pPp2/3P4/2N5/PPP2PPP/R1BQKBNR w KQkq f6 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		IBitboard attacks = piece.getAttacks(Bitboard.getSingleBit(Square.getIndex(e5)), board);
		String expected =
			"""
			00000000
			00000000
			00010100
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldErrorIfSourceSquareDoesNotHavePiece() {
		int src = Square.getIndex(a1);
		int dst = Square.getIndex(b1);
		try {
			piece.move(src, dst, board, reader);
		} catch (Error e) {
			assertEquals("Invalid move", e.getMessage());
		}
	}

	@Test
	void shouldErrorIfPieceCanNotMoveToDestination() {
		board = new Board();
		int src = Square.getIndex(a2);
		int dst = Square.getIndex(a3);
		try {
			piece.move(src, dst, board, reader);
		} catch (Error e) {
			assertEquals("Invalid move", e.getMessage());
		}
	}
}

package piece.pawn;

import static api.model.board.Square.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import api.model.bits.IBitboard;
import api.model.board.IBoard;
import api.model.piece.Pieces;
import api.model.reader.IReader;
import bits.Bitboard;
import board.Board;
import fen.ForsythEdwardsNotation;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reader.Reader;

public class WhitePawnTest {

	private WhitePawn piece;
	private IReader reader;
	private IBoard board;

	@BeforeEach
	void setup() {
		String inputString = "Q";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		board = new Board();
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
	}

	@Test
	void shouldGetTargetsFromBaseRank() {
		IBitboard attacks = piece.getTargets(Bitboard.getSingleBit(Board.getIndex(e2)), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00001000
			00001000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetTargetsFromBaseRankWithEnemyObstacle() {
		String fen = "rnbqkbnr/pppp1ppp/8/8/4p3/2N3P1/PPPPPP1P/R1BQKBNR w KQkq - 0 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		IBitboard attacks = piece.getTargets(Bitboard.getSingleBit(Board.getIndex(e2)), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00001000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetTargetsFromBaseRankWithImmediateEnemyObstacle() {
		String fen = "rnbqkbnr/pppp1ppp/8/8/8/2N1pNP1/PPPPPP1P/R1BQKB1R w KQkq - 0 4";
		board = new Board(new ForsythEdwardsNotation(fen));
		IBitboard attacks = piece.getTargets(Bitboard.getSingleBit(Board.getIndex(e2)), board);
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
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetTargetsFromBaseRankWithImmediateFriendlyObstacle() {
		String fen = "rnbqkbnr/pppp1ppp/8/8/8/2N1pNP1/PPPPPP1P/R1BQKB1R w KQkq - 0 4";
		board = new Board(new ForsythEdwardsNotation(fen));
		IBitboard attacks = piece.getTargets(Bitboard.getSingleBit(Board.getIndex(f2)), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00001000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldNotGetAttacksWithoutEnemy() {
		board = new Board(new ForsythEdwardsNotation("8/8/8/8/8/8/8/8 w - - 0 1"));
		IBitboard attacks = piece.getAttacks(Bitboard.getSingleBit(Board.getIndex(e2)), board);
		assertEquals(0, attacks.getBits());
	}

	@Test
	void shouldGetAttacksWithEnemy() {
		String fen = "rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		IBitboard attacks = piece.getAttacks(Bitboard.getSingleBit(Board.getIndex(e4)), board);
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
		IBitboard attacks = piece.getAttacks(Bitboard.getSingleBit(Board.getIndex(e4)), board);
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
		IBitboard attacks = piece.getAttacks(Bitboard.getSingleBit(Board.getIndex(e5)), board);
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
		int src = Board.getIndex(a1);
		int dst = Board.getIndex(b1);
		try {
			piece.move(src, dst, board, reader);
		} catch (Error e) {
			assertEquals("Invalid move", e.getMessage());
		}
	}

	@Test
	void shouldErrorIfPieceCanNotMoveToDestination() {
		board = new Board();
		int src = Board.getIndex(e5);
		int dst = Board.getIndex(e6);
		try {
			piece.move(src, dst, board, reader);
		} catch (Error e) {
			assertEquals("Invalid move", e.getMessage());
		}
	}

	@Test
	void shouldNotLeaveKingUnsafe() {
		String fen = "rnbqk1nr/pppp1ppp/8/4p3/1b2P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		int src = Board.getIndex(d2);
		int dst = Board.getIndex(d3);
		try {
			piece.move(src, dst, board, reader);
		} catch (Error e) {
			assertEquals("King is in check", e.getMessage());
		}
	}

	@Test
	void shouldSinglePush() {
		int src = Board.getIndex(e2);
		int dst = Board.getIndex(e3);
		piece.move(src, dst, board, reader);
	}

	@Test
	void shouldDoublePush() {
		int src = Board.getIndex(e2);
		int dst = Board.getIndex(e4);
		piece.move(src, dst, board, reader);
	}

	@Test
	void shouldCapture() {
		String fen = "rnb1kbnr/ppp3pp/3qp3/3pPp2/3P4/2N5/PPP2PPP/R1BQKBNR w KQkq f6 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		int src = Board.getIndex(e5);
		int dst = Board.getIndex(d6);
		piece.move(src, dst, board, reader);
	}

	@Test
	void shouldEnPassant() {
		String fen = "rnb1kbnr/ppp3pp/3qp3/3pPp2/3P4/2N5/PPP2PPP/R1BQKBNR w KQkq f6 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		int src = Board.getIndex(e5);
		int dst = Board.getIndex(f6);
		piece.move(src, dst, board, reader);
	}

	@Test
	void shouldRepromptIfInvalidPromotionSelection() {
		String fen = "rnbqk2r/ppp2pPp/3p1n2/2b1p3/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		String inputString = "X\nR";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(g7);
		int dst = Board.getIndex(g8);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.WhiteRook, board.getPiece(g8).getVariant());
	}

	@Test
	void shouldPromoteToQueen() {
		String fen = "rnbqk2r/ppp2pPp/3p1n2/2b1p3/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		int src = Board.getIndex(g7);
		int dst = Board.getIndex(g8);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.WhiteQueen, board.getPiece(g8).getVariant());
	}

	@Test
	void shouldPromoteToRook() {
		String fen = "rnbqk2r/ppp2pPp/3p1n2/2b1p3/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		String inputString = "R";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(g7);
		int dst = Board.getIndex(g8);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.WhiteRook, board.getPiece(g8).getVariant());
	}

	@Test
	void shouldPromoteToBishop() {
		String fen = "rnbqk2r/ppp2pPp/3p1n2/2b1p3/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		String inputString = "B";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(g7);
		int dst = Board.getIndex(g8);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.WhiteBishop, board.getPiece(g8).getVariant());
	}

	@Test
	void shouldPromoteToKnight() {
		String fen = "rnbqk2r/ppp2pPp/3p1n2/2b1p3/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		String inputString = "N";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(g7);
		int dst = Board.getIndex(g8);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.WhiteKnight, board.getPiece(g8).getVariant());
	}

	@Test
	void shouldPromoteCaptureToQueen() {
		String fen = "rnbqk2r/ppp2pPp/3p1n2/2b1p3/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		int src = Board.getIndex(g7);
		int dst = Board.getIndex(h8);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.WhiteQueen, board.getPiece(h8).getVariant());
	}

	@Test
	void shouldPromoteCaptureToRook() {
		String fen = "rnbqk2r/ppp2pPp/3p1n2/2b1p3/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		String inputString = "R";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(g7);
		int dst = Board.getIndex(h8);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.WhiteRook, board.getPiece(h8).getVariant());
	}

	@Test
	void shouldPromoteCaptureToBishop() {
		String fen = "rnbqk2r/ppp2pPp/3p1n2/2b1p3/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		String inputString = "B";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(g7);
		int dst = Board.getIndex(h8);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.WhiteBishop, board.getPiece(h8).getVariant());
	}

	@Test
	void shouldPromoteCaptureToKnight() {
		String fen = "rnbqk2r/ppp2pPp/3p1n2/2b1p3/8/8/PPPPPPP1/RNBQKBNR w KQkq - 0 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhitePawn(board.getWhitePawn().getBitboard());
		String inputString = "N";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(g7);
		int dst = Board.getIndex(h8);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.WhiteKnight, board.getPiece(h8).getVariant());
	}
}

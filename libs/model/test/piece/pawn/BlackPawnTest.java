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

public class BlackPawnTest {

	private BlackPawn piece;
	private IReader reader;
	private IBoard board;

	@BeforeEach
	void setup() {
		String inputString = "Q";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		board = new Board();
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
	}

	@Test
	void shouldGetTargetsFromBaseRank() {
		IBitboard attacks = piece.getTargets(Bitboard.getSingleBit(Board.getIndex(e7)), board);
		String expected =
			"""
			00000000
			00000000
			00001000
			00001000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetTargetsFromBaseRankWithEnemyObstacle() {
		String fen = "rnbqkb1r/pppppppp/5n2/4P3/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2";
		board = new Board(new ForsythEdwardsNotation(fen));
		IBitboard attacks = piece.getTargets(Bitboard.getSingleBit(Board.getIndex(e7)), board);
		String expected =
			"""
			00000000
			00000000
			00001000
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetTargetsFromBaseRankWithImmediateEnemyObstacle() {
		String fen = "r1bqkb1r/pppppppp/2n1Pn2/8/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		IBitboard attacks = piece.getTargets(Bitboard.getSingleBit(Board.getIndex(e7)), board);
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
		String fen = "r1bqkb1r/pppppppp/2n1Pn2/8/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		IBitboard attacks = piece.getTargets(Bitboard.getSingleBit(Board.getIndex(f7)), board);
		String expected =
			"""
			00000000
			00000000
			00001000
			00000000
			00000000
			00000000
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
		String fen = "rnbqkbnr/pppp1ppp/8/4p3/3PP3/8/PPP2PPP/RNBQKBNR b KQkq d3 0 2";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		IBitboard attacks = piece.getAttacks(Bitboard.getSingleBit(Board.getIndex(e5)), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00010000
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAttacksWithEnemies() {
		String fen = "r1bqkbnr/pppp1ppp/2n5/4p3/3PPP2/8/PPP3PP/RNBQKBNR b KQkq f3 0 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		IBitboard attacks = piece.getAttacks(Bitboard.getSingleBit(Board.getIndex(e5)), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00010100
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetEnPassantAttack() {
		String fen = "rnbqkbnr/pppp1ppp/8/8/3Pp3/2N2N2/PPP1PPPP/R1BQKB1R b KQkq d3 0 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		IBitboard attacks = piece.getAttacks(Bitboard.getSingleBit(Board.getIndex(e4)), board);
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00010100
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
		String fen = "rnbqkbnr/ppp1pppp/3p4/7Q/4P3/8/PPPP1PPP/RNB1KBNR b KQkq - 1 2";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		int src = Board.getIndex(f7);
		int dst = Board.getIndex(f6);
		try {
			piece.move(src, dst, board, reader);
		} catch (Error e) {
			assertEquals("King is in check", e.getMessage());
		}
	}

	@Test
	void shouldSinglePush() {
		String fen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		int src = Board.getIndex(e7);
		int dst = Board.getIndex(e6);
		piece.move(src, dst, board, reader);
	}

	@Test
	void shouldDoublePush() {
		String fen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		int src = Board.getIndex(e7);
		int dst = Board.getIndex(e5);
		piece.move(src, dst, board, reader);
	}

	@Test
	void shouldCapture() {
		String fen = "rnbqkbnr/ppp1pppp/8/3p4/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		int src = Board.getIndex(d5);
		int dst = Board.getIndex(e4);
		piece.move(src, dst, board, reader);
	}

	@Test
	void shouldEnPassant() {
		String fen = "rnbqkbnr/ppp1pppp/8/8/2PpP3/5N2/PP1P1PPP/RNBQKB1R b KQkq c3 0 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		int src = Board.getIndex(d4);
		int dst = Board.getIndex(c3);
		piece.move(src, dst, board, reader);
	}

	@Test
	void shouldRepromptIfInvalidPromotionSelection() {
		String fen = "rnbqkbnr/1ppppppp/8/8/3PP3/2N2N2/PpPB1PPP/R2QKB1R b KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		String inputString = "X\nR";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(b2);
		int dst = Board.getIndex(b1);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.BlackRook, board.getPiece(b1).getVariant());
	}

	@Test
	void shouldPromoteToQueen() {
		String fen = "rnbqkbnr/1ppppppp/8/8/3PP3/2N2N2/PpPB1PPP/R2QKB1R b KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		int src = Board.getIndex(b2);
		int dst = Board.getIndex(b1);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.BlackQueen, board.getPiece(b1).getVariant());
	}

	@Test
	void shouldPromoteToRook() {
		String fen = "rnbqkbnr/1ppppppp/8/8/3PP3/2N2N2/PpPB1PPP/R2QKB1R b KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		String inputString = "R";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(b2);
		int dst = Board.getIndex(b1);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.BlackRook, board.getPiece(b1).getVariant());
	}

	@Test
	void shouldPromoteToBishop() {
		String fen = "rnbqkbnr/1ppppppp/8/8/3PP3/2N2N2/PpPB1PPP/R2QKB1R b KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		String inputString = "B";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(b2);
		int dst = Board.getIndex(b1);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.BlackBishop, board.getPiece(b1).getVariant());
	}

	@Test
	void shouldPromoteToKnight() {
		String fen = "rnbqkbnr/1ppppppp/8/8/3PP3/2N2N2/PpPB1PPP/R2QKB1R b KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		String inputString = "N";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(b2);
		int dst = Board.getIndex(b1);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.BlackKnight, board.getPiece(b1).getVariant());
	}

	@Test
	void shouldPromoteCaptureToQueen() {
		String fen = "rnbqkbnr/1ppppppp/8/8/3PP3/2N2N2/PpPB1PPP/R2QKB1R b KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		int src = Board.getIndex(b2);
		int dst = Board.getIndex(a1);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.BlackQueen, board.getPiece(a1).getVariant());
	}

	@Test
	void shouldPromoteCaptureToRook() {
		String fen = "rnbqkbnr/1ppppppp/8/8/3PP3/2N2N2/PpPB1PPP/R2QKB1R b KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		String inputString = "R";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(b2);
		int dst = Board.getIndex(a1);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.BlackRook, board.getPiece(a1).getVariant());
	}

	@Test
	void shouldPromoteCaptureToBishop() {
		String fen = "rnbqkbnr/1ppppppp/8/8/3PP3/2N2N2/PpPB1PPP/R2QKB1R b KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		String inputString = "B";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(b2);
		int dst = Board.getIndex(a1);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.BlackBishop, board.getPiece(a1).getVariant());
	}

	@Test
	void shouldPromoteCaptureToKnight() {
		String fen = "rnbqkbnr/1ppppppp/8/8/3PP3/2N2N2/PpPB1PPP/R2QKB1R b KQkq - 1 5";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new BlackPawn(board.getBlackPawn().getBitboard());
		String inputString = "N";
		byte[] bytes = inputString.getBytes();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		int src = Board.getIndex(b2);
		int dst = Board.getIndex(a1);
		piece.move(src, dst, board, reader);
		assertEquals(Pieces.BlackKnight, board.getPiece(a1).getVariant());
	}
}

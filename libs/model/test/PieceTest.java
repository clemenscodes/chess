import static api.model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import api.model.bits.IBitboard;
import api.model.board.IBoard;
import api.model.piece.Pieces;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PieceTest {

	private IBoard board;
	private Piece piece;

	@BeforeEach
	void setup() {
		board = new Board(new ForsythEdwardsNotation("8/8/8/8/8/8/8/8 w - - 0 1"));
		piece = new PieceMock(Pieces.WhitePawn);
	}

	static class PieceMock extends Piece {

		public PieceMock(Pieces variant) {
			super(variant);
		}

		public PieceMock(Pieces variant, IBitboard bitboard) {
			super(variant, bitboard);
		}

		public IBitboard getAttacks(IBitboard piece, IBoard board) {
			return removeFriendlyPieces(
				Bitboard.merge(piece, Bitboard.negate(Board.firstRank)),
				board
			);
		}
	}

	@Test
	void shouldGetBitboard() {
		IBitboard bitboard = piece.getBitboard();
		IBitboard expected = new Bitboard();
		assertEquals(expected.getBits(), bitboard.getBits());
	}

	@Test
	void shouldGetVariant() {
		Pieces variant = piece.getVariant();
		Pieces expected = Pieces.WhitePawn;
		assertEquals(expected, variant);
	}

	@Test
	void shouldGetMoveMask() {
		IBitboard moveMask = piece.getMoveMask(Board.getIndex(a1), Board.getIndex(b1));
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			11000000""";
		assertEquals(expected, moveMask.toString());
	}

	@Test
	void shouldGetAllAttacks() {
		IBitboard attacks = piece.getAllAttacks(board);
		IBitboard expected = new Bitboard();
		assertEquals(expected.getBits(), attacks.getBits());
	}

	@Test
	void shouldErrorIfSourceSquareDoesNotHavePiece() {
		int src = Board.getIndex(a1);
		int dst = Board.getIndex(b1);
		try {
			piece.move(src, dst, board);
		} catch (Error e) {
			assertEquals("Invalid move", e.getMessage());
		}
	}

	@Test
	void shouldErrorIfPieceCanNotMoveToDestination() {
		board = new Board(new ForsythEdwardsNotation());
		piece = new PieceMock(Pieces.WhitePawn, board.getWhitePawn().getBitboard());
		int src = Board.getIndex(a2);
		int dst = Board.getIndex(a3);
		try {
			piece.move(src, dst, board);
		} catch (Error e) {
			assertEquals("Invalid move", e.getMessage());
		}
	}

	@Test
	void shouldErrorIfKingUnsafe() {
		String fen = "rnbqk1nr/pppp1ppp/8/4p3/1b2P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new PieceMock(Pieces.WhitePawn, board.getWhitePawn().getBitboard());
		int src = Board.getIndex(d2);
		int dst = Board.getIndex(d3);
		try {
			piece.move(src, dst, board);
		} catch (Error e) {
			assertEquals("King is in check", e.getMessage());
		}
	}

	@Test
	void shouldCaptureIfDestinationOverlapsWithEnemy() {
		String fen = "rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new PieceMock(Pieces.WhitePawn, board.getWhitePawn().getBitboard());
		int src = Board.getIndex(e4);
		int dst = Board.getIndex(d5);
		piece.move(src, dst, board);
	}

	@Test
	void shouldGetMoves() {
		String fen = "rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2";
		board = new Board(new ForsythEdwardsNotation(fen));
		piece = new WhiteKnight(board.getWhiteKnight().getBitboard());
		var moves = piece.getMoves(board);
		StringBuilder builder = new StringBuilder();
		for (var move : moves) {
			builder.append(move[0].name()).append(move[1].name()).append("\n");
		}
		String expected =
			"""
			b1a3
			b1c3
			g1e2
			g1f3
			g1h3
			""";
		assertEquals(expected, builder.toString());
	}
}

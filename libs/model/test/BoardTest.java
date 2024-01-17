import static api.model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import api.model.bits.IBitboard;
import api.model.board.IBoard;
import api.model.piece.Pieces;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class BoardTest {

	@Test
	void shouldInitialize() {
		String fen = "r2qk2r/pppbbppp/2np4/1B2pn2/3PP3/2N2N2/PPPB1PPP/R2QK2R w KQkq - 0 1";
		assertDoesNotThrow(() -> new Board());
		assertDoesNotThrow(() -> new Board(new ForsythEdwardsNotation()));
		assertDoesNotThrow(() -> new Board(new ForsythEdwardsNotation(fen)));
	}

	@Test
	void shouldGetPiecePlacementData() {
		String fen = "r2qk2r/pppbbppp/2np4/1B2pn2/3PP3/2N2N2/PPPB1PPP/R2QK2R w KQkq - 0 1";
		IBoard board = new Board(new ForsythEdwardsNotation(fen));
		assertEquals(
			"r2qk2r/pppbbppp/2np4/1B2pn2/3PP3/2N2N2/PPPB1PPP/R2QK2R",
			board.getPiecePlacementData()
		);
	}

	@Test
	void shouldPrintBoard() {
		String fen = "r2qk2r/pppbbppp/2np4/1B2pn2/3PP3/2N2N2/PPPB1PPP/R2QK2R w KQkq - 0 1";
		IBoard board = new Board(new ForsythEdwardsNotation(fen));
		String expected =
			"""
			[♜] [ ] [ ] [♛] [♚] [ ] [ ] [♜]
			[♟] [♟] [♟] [♝] [♝] [♟] [♟] [♟]
			[ ] [ ] [♞] [♟] [ ] [ ] [ ] [ ]
			[ ] [♗] [ ] [ ] [♟] [♞] [ ] [ ]
			[ ] [ ] [ ] [♙] [♙] [ ] [ ] [ ]
			[ ] [ ] [♘] [ ] [ ] [♘] [ ] [ ]
			[♙] [♙] [♙] [♗] [ ] [♙] [♙] [♙]
			[♖] [ ] [ ] [♕] [♔] [ ] [ ] [♖]""";
		assertEquals(expected, board.toString());
	}

	@Test
	void testGetKing() {
		IBoard board = new Board();
		assertEquals(board.getWhiteKing().getBitboard(), board.getKing(true));
		board.getFen().switchActiveColor();
		assertEquals(board.getBlackKing().getBitboard(), board.getKing(false));
	}

	@Test
	void testGetPieceByIndex() {
		IBoard board = new Board();
		assertEquals(Pieces.WhiteRook, board.getPieceByIndex(Board.getIndex(a1)));
		assertEquals(Pieces.BlackPawn, board.getPieceByIndex(Board.getIndex(d7)));
		try {
			board.getPieceByIndex(Board.getIndex(e4));
		} catch (Error e) {
			assertEquals(e.getMessage(), "No piece is set on the square e4");
		}
	}

	@Test
	void testGetWhitePieces() {
		IBoard board = new Board();
		assertNotNull(board.getWhitePieces());
	}

	@Test
	void testGetBlackPieces() {
		IBoard board = new Board();
		assertNotNull(board.getBlackPieces());
	}

	@Test
	void testGetPieces() {
		IBoard board = new Board();
		assertEquals(board.getWhitePieces().getBits(), board.getPieces(true).getBits());
		board.getFen().switchActiveColor();
		assertEquals(board.getBlackPieces().getBits(), board.getPieces(false).getBits());
	}

	@Test
	void testGetOccupiedSquares() {
		IBoard board = new Board();
		assertNotNull(board.getOccupiedSquares());
	}

	@Test
	void testGetEmptySquares() {
		IBoard board = new Board();
		assertNotNull(board.getEmptySquares());
	}

	@Test
	void testDeepCopy() throws IOException, ClassNotFoundException {
		IBoard board = new Board();
		IBoard copy = board.deepCopy();
		assertNotSame(board, copy);
	}

	@Test
	void testKingUnsafe() {
		IBoard board = new Board();
		System.out.println(board);
		assertFalse(board.kingUnsafe());
	}

	@Test
	void testIsSquareAttacked() {
		IBoard board = new Board();
		assertFalse(board.isSquareAttacked(a1));
	}

	@Test
	void testIsSquareEmpty() {
		IBoard board = new Board();
		assertFalse(board.isSquareEmpty(a1));
	}

	@Test
	void testCapturePiece() {
		IBoard board = new Board();
		int index = Board.getIndex(a1);
		board.capturePiece(index);
		assert (Bitboard.overlap(Bitboard.getSingleBit(index), board.getEmptySquares()));
	}

	@Test
	void shouldGetAllOpponentAttacks() {
		IBoard board = new Board();
		IBitboard attacks = board.getAllOpponentAttacks();
		String expected =
			"""
			00000000
			00000000
			10100101
			00000000
			00000000
			00000000
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}

	@Test
	void shouldGetAllFriendlyAttacks() {
		IBoard board = new Board();
		IBitboard attacks = board.getAllFriendlyAttacks();
		String expected =
			"""
			00000000
			00000000
			00000000
			00000000
			00000000
			10100101
			00000000
			00000000""";
		assertEquals(expected, attacks.toString());
	}
}

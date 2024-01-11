package model.board;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.fen.ForsythEdwardsNotation;
import model.piece.Pieces;
import org.junit.jupiter.api.Test;

public class BoardTest {

	@Test
	void shouldInitialize() {
		assertDoesNotThrow(() -> new Board());
		assertDoesNotThrow(() -> new Board(new ForsythEdwardsNotation()));
	}

	@Test
	void testGetOwnKing() {
		IBoard board = new Board();
		assertEquals(board.getWhiteKing().getBitboard(), board.getOwnKing());
		board.getFen().switchActiveColor();
		assertEquals(board.getBlackKing().getBitboard(), board.getOwnKing());
	}

	@Test
	void testGetPieceByIndex() {
		IBoard board = new Board();
		assertEquals(Pieces.WhiteRook, board.getPieceByIndex(Square.getIndex(a1)));
		assertEquals(Pieces.BlackPawn, board.getPieceByIndex(Square.getIndex(d7)));
		try {
			board.getPieceByIndex(Square.getIndex(e4));
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
	void testGetOpponentPieces() {
		IBoard board = new Board();
		assertEquals(board.getBlackPieces().getBits(), board.getOpponentPieces().getBits());
		board.getFen().switchActiveColor();
		assertEquals(board.getWhitePieces().getBits(), board.getOpponentPieces().getBits());
	}

	@Test
	void testGetFriendlyPieces() {
		IBoard board = new Board();
		assertEquals(board.getWhitePieces().getBits(), board.getFriendlyPieces().getBits());
		board.getFen().switchActiveColor();
		assertEquals(board.getBlackPieces().getBits(), board.getFriendlyPieces().getBits());
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
	void testGetAllFriendlyPieces() {
		IBoard board = new Board();
		IBitboard friendlyPieces = board.getFriendlyPieces();
		assertEquals(friendlyPieces.getBits(), board.getWhitePieces().getBits());
		board.getFen().switchActiveColor();
		friendlyPieces = board.getFriendlyPieces();
		assertEquals(friendlyPieces.getBits(), board.getBlackPieces().getBits());
	}

	@Test
	void testCapturePiece() {
		IBoard board = new Board();
		int index = Square.getIndex(a1);
		board.capturePiece(index);
		assert (Bitboard.overlap(Bitboard.getSingleBit(index), board.getEmptySquares()));
	}
}

package model;

import static org.junit.jupiter.api.Assertions.*;

import api.model.Pieces;
import org.junit.jupiter.api.Test;

public class PiecesTest {

	@Test
	void testFromSymbol() {
		assertEquals(Pieces.BlackRook, Piece.fromSymbol('r'));
		assertEquals(Pieces.BlackKnight, Piece.fromSymbol('n'));
		assertEquals(Pieces.BlackBishop, Piece.fromSymbol('b'));
		assertEquals(Pieces.BlackQueen, Piece.fromSymbol('q'));
		assertEquals(Pieces.BlackKing, Piece.fromSymbol('k'));
		assertEquals(Pieces.BlackPawn, Piece.fromSymbol('p'));
		assertEquals(Pieces.WhiteRook, Piece.fromSymbol('R'));
		assertEquals(Pieces.WhiteKnight, Piece.fromSymbol('N'));
		assertEquals(Pieces.WhiteBishop, Piece.fromSymbol('B'));
		assertEquals(Pieces.WhiteQueen, Piece.fromSymbol('Q'));
		assertEquals(Pieces.WhiteKing, Piece.fromSymbol('K'));
		assertEquals(Pieces.WhitePawn, Piece.fromSymbol('P'));
		assertThrows(IllegalStateException.class, () -> Piece.fromSymbol('x'));
	}

	@Test
	void testFromKind() {
		assertEquals('r', Piece.fromKind(Pieces.BlackRook));
		assertEquals('n', Piece.fromKind(Pieces.BlackKnight));
		assertEquals('b', Piece.fromKind(Pieces.BlackBishop));
		assertEquals('q', Piece.fromKind(Pieces.BlackQueen));
		assertEquals('k', Piece.fromKind(Pieces.BlackKing));
		assertEquals('p', Piece.fromKind(Pieces.BlackPawn));
		assertEquals('R', Piece.fromKind(Pieces.WhiteRook));
		assertEquals('N', Piece.fromKind(Pieces.WhiteKnight));
		assertEquals('B', Piece.fromKind(Pieces.WhiteBishop));
		assertEquals('Q', Piece.fromKind(Pieces.WhiteQueen));
		assertEquals('K', Piece.fromKind(Pieces.WhiteKing));
		assertEquals('P', Piece.fromKind(Pieces.WhitePawn));
	}

	@Test
	void testGetWhitePromotionPieces() {
		Pieces[] whitePromotionPieces = Piece.getWhitePromotionPieces();
		assertNotNull(whitePromotionPieces);
		assertEquals(4, whitePromotionPieces.length);
		assertEquals(Pieces.WhiteQueen, whitePromotionPieces[0]);
		assertEquals(Pieces.WhiteRook, whitePromotionPieces[1]);
		assertEquals(Pieces.WhiteKnight, whitePromotionPieces[2]);
		assertEquals(Pieces.WhiteBishop, whitePromotionPieces[3]);
	}

	@Test
	void testGetBlackPromotionPieces() {
		Pieces[] blackPromotionPieces = Piece.getBlackPromotionPieces();
		assertNotNull(blackPromotionPieces);
		assertEquals(4, blackPromotionPieces.length);
		assertEquals(Pieces.BlackQueen, blackPromotionPieces[0]);
		assertEquals(Pieces.BlackRook, blackPromotionPieces[1]);
		assertEquals(Pieces.BlackKnight, blackPromotionPieces[2]);
		assertEquals(Pieces.BlackBishop, blackPromotionPieces[3]);
	}

	@Test
	void testGetSelectedPiece() {
		Pieces[] testPieces = {
			Pieces.WhiteQueen,
			Pieces.WhiteRook,
			Pieces.WhiteKnight,
			Pieces.WhiteBishop,
		};
		assertEquals(Pieces.WhiteQueen, Piece.getSelectedPiece(testPieces, "Q"));
		assertEquals(Pieces.WhiteRook, Piece.getSelectedPiece(testPieces, "R"));
		assertEquals(Pieces.WhiteKnight, Piece.getSelectedPiece(testPieces, "N"));
		assertEquals(Pieces.WhiteBishop, Piece.getSelectedPiece(testPieces, "B"));
		assertThrows(Error.class, () -> Piece.getSelectedPiece(testPieces, "Invalid"));
	}

	@Test
	void testPieceByIndex() {
		assertEquals(Pieces.BlackRook, Piece.PIECE_BY_INDEX[0]);
		assertEquals(Pieces.BlackKnight, Piece.PIECE_BY_INDEX[1]);
		assertEquals(Pieces.BlackBishop, Piece.PIECE_BY_INDEX[2]);
		assertEquals(Pieces.BlackQueen, Piece.PIECE_BY_INDEX[3]);
		assertEquals(Pieces.BlackKing, Piece.PIECE_BY_INDEX[4]);
		assertEquals(Pieces.BlackPawn, Piece.PIECE_BY_INDEX[5]);
		assertEquals(Pieces.WhiteRook, Piece.PIECE_BY_INDEX[6]);
		assertEquals(Pieces.WhiteKnight, Piece.PIECE_BY_INDEX[7]);
		assertEquals(Pieces.WhiteBishop, Piece.PIECE_BY_INDEX[8]);
		assertEquals(Pieces.WhiteQueen, Piece.PIECE_BY_INDEX[9]);
		assertEquals(Pieces.WhiteKing, Piece.PIECE_BY_INDEX[10]);
		assertEquals(Pieces.WhitePawn, Piece.PIECE_BY_INDEX[11]);
	}

	@Test
	void testEmptySymbol() {
		assertEquals(' ', Piece.EMPTY_SYMBOL);
	}
}

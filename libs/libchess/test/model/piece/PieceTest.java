package model.piece;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PieceTest {

	@Test
	void testFromSymbol() {
		assertEquals(Pieces.BlackRook, Pieces.fromSymbol('r'));
		assertEquals(Pieces.BlackKnight, Pieces.fromSymbol('n'));
		assertEquals(Pieces.BlackBishop, Pieces.fromSymbol('b'));
		assertEquals(Pieces.BlackQueen, Pieces.fromSymbol('q'));
		assertEquals(Pieces.BlackKing, Pieces.fromSymbol('k'));
		assertEquals(Pieces.BlackPawn, Pieces.fromSymbol('p'));
		assertEquals(Pieces.WhiteRook, Pieces.fromSymbol('R'));
		assertEquals(Pieces.WhiteKnight, Pieces.fromSymbol('N'));
		assertEquals(Pieces.WhiteBishop, Pieces.fromSymbol('B'));
		assertEquals(Pieces.WhiteQueen, Pieces.fromSymbol('Q'));
		assertEquals(Pieces.WhiteKing, Pieces.fromSymbol('K'));
		assertEquals(Pieces.WhitePawn, Pieces.fromSymbol('P'));
		assertThrows(IllegalStateException.class, () -> Pieces.fromSymbol('x'));
	}

	@Test
	void testGetWhitePromotionPieces() {
		Pieces[] whitePromotionPieces = Pieces.getWhitePromotionPieces();
		assertNotNull(whitePromotionPieces);
		assertEquals(4, whitePromotionPieces.length);
		assertEquals(Pieces.WhiteQueen, whitePromotionPieces[0]);
		assertEquals(Pieces.WhiteRook, whitePromotionPieces[1]);
		assertEquals(Pieces.WhiteKnight, whitePromotionPieces[2]);
		assertEquals(Pieces.WhiteBishop, whitePromotionPieces[3]);
	}

	@Test
	void testGetBlackPromotionPieces() {
		Pieces[] blackPromotionPieces = Pieces.getBlackPromotionPieces();
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
		assertEquals(Pieces.WhiteQueen, Pieces.getSelectedPiece(testPieces, "Q"));
		assertEquals(Pieces.WhiteRook, Pieces.getSelectedPiece(testPieces, "R"));
		assertEquals(Pieces.WhiteKnight, Pieces.getSelectedPiece(testPieces, "N"));
		assertEquals(Pieces.WhiteBishop, Pieces.getSelectedPiece(testPieces, "B"));
		assertThrows(Error.class, () -> Pieces.getSelectedPiece(testPieces, "Invalid"));
	}

	@Test
	void testPieceByIndex() {
		assertEquals(Pieces.BlackRook, Pieces.PIECE_BY_INDEX[0]);
		assertEquals(Pieces.BlackKnight, Pieces.PIECE_BY_INDEX[1]);
		assertEquals(Pieces.BlackBishop, Pieces.PIECE_BY_INDEX[2]);
		assertEquals(Pieces.BlackQueen, Pieces.PIECE_BY_INDEX[3]);
		assertEquals(Pieces.BlackKing, Pieces.PIECE_BY_INDEX[4]);
		assertEquals(Pieces.BlackPawn, Pieces.PIECE_BY_INDEX[5]);
		assertEquals(Pieces.WhiteRook, Pieces.PIECE_BY_INDEX[6]);
		assertEquals(Pieces.WhiteKnight, Pieces.PIECE_BY_INDEX[7]);
		assertEquals(Pieces.WhiteBishop, Pieces.PIECE_BY_INDEX[8]);
		assertEquals(Pieces.WhiteQueen, Pieces.PIECE_BY_INDEX[9]);
		assertEquals(Pieces.WhiteKing, Pieces.PIECE_BY_INDEX[10]);
		assertEquals(Pieces.WhitePawn, Pieces.PIECE_BY_INDEX[11]);
	}

	@Test
	void testEmptySymbol() {
		assertEquals(' ', Pieces.EMPTY_SYMBOL);
	}
}

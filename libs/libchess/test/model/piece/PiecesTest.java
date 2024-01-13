package model.piece;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PiecesTest {

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
		assertThrows(IllegalStateException.class, () -> Pieces.fromSymbol('!'));
		assertThrows(IllegalStateException.class, () -> Pieces.fromSymbol('1'));
		assertThrows(IllegalStateException.class, () -> Pieces.fromSymbol('@'));
		assertThrows(IllegalStateException.class, () -> Pieces.fromSymbol('z'));
	}

	@Test
	void testFromKind() {
		assertEquals('r', Pieces.fromKind(Pieces.BlackRook));
		assertEquals('n', Pieces.fromKind(Pieces.BlackKnight));
		assertEquals('b', Pieces.fromKind(Pieces.BlackBishop));
		assertEquals('q', Pieces.fromKind(Pieces.BlackQueen));
		assertEquals('k', Pieces.fromKind(Pieces.BlackKing));
		assertEquals('p', Pieces.fromKind(Pieces.BlackPawn));
		assertEquals('R', Pieces.fromKind(Pieces.WhiteRook));
		assertEquals('N', Pieces.fromKind(Pieces.WhiteKnight));
		assertEquals('B', Pieces.fromKind(Pieces.WhiteBishop));
		assertEquals('Q', Pieces.fromKind(Pieces.WhiteQueen));
		assertEquals('K', Pieces.fromKind(Pieces.WhiteKing));
		assertEquals('P', Pieces.fromKind(Pieces.WhitePawn));
	}

	@Test
	void testGetWhitePromotionPieces() {
		Pieces[] whitePromotionPieces = Pieces.getWhitePromotionPieces();
		assertNotNull(whitePromotionPieces);
		assertEquals(4, whitePromotionPieces.length);
	}

	@Test
	void testGetBlackPromotionPieces() {
		Pieces[] blackPromotionPieces = Pieces.getBlackPromotionPieces();
		assertNotNull(blackPromotionPieces);
		assertEquals(4, blackPromotionPieces.length);
	}

	@Test
	void testGetSelectedWhitePiece() {
		Pieces[] pieces = Pieces.getWhitePromotionPieces();
		assertEquals(Pieces.WhiteQueen, Pieces.getSelectedPiece(pieces, "Q"));
		assertEquals(Pieces.WhiteRook, Pieces.getSelectedPiece(pieces, "R"));
		assertEquals(Pieces.WhiteBishop, Pieces.getSelectedPiece(pieces, "B"));
		assertEquals(Pieces.WhiteKnight, Pieces.getSelectedPiece(pieces, "N"));
		assertThrows(Error.class, () -> Pieces.getSelectedPiece(pieces, "InvalidInput"));
	}

	@Test
	void testGetSelectedBlackPiece() {
		Pieces[] pieces = Pieces.getBlackPromotionPieces();
		assertEquals(Pieces.BlackQueen, Pieces.getSelectedPiece(pieces, "Q"));
		assertEquals(Pieces.BlackRook, Pieces.getSelectedPiece(pieces, "R"));
		assertEquals(Pieces.BlackBishop, Pieces.getSelectedPiece(pieces, "B"));
		assertEquals(Pieces.BlackKnight, Pieces.getSelectedPiece(pieces, "N"));
		assertThrows(Error.class, () -> Pieces.getSelectedPiece(pieces, "InvalidInput"));
	}

	@Test
	void testPieceByIndex() {
		assertEquals(Pieces.BlackRook, Pieces.PIECE_BY_INDEX[0]);
		assertEquals(Pieces.WhitePawn, Pieces.PIECE_BY_INDEX[11]);
	}

	@Test
	void testEmptySymbol() {
		assertEquals(' ', Pieces.EMPTY_SYMBOL);
	}
}

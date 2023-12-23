package model.piece;

public enum Pieces {
	WhitePawn,
	WhiteBishop,
	WhiteKnight,
	WhiteRook,
	WhiteQueen,
	WhiteKing,
	BlackPawn,
	BlackBishop,
	BlackKnight,
	BlackRook,
	BlackQueen,
	BlackKing;

	public static Pieces fromSymbol(char symbol) {
		return switch (symbol) {
			case 'r' -> BlackRook;
			case 'n' -> BlackKnight;
			case 'b' -> BlackBishop;
			case 'q' -> BlackQueen;
			case 'k' -> BlackKing;
			case 'p' -> BlackPawn;
			case 'R' -> WhiteRook;
			case 'N' -> WhiteKnight;
			case 'B' -> WhiteBishop;
			case 'Q' -> WhiteQueen;
			case 'K' -> WhiteKing;
			case 'P' -> WhitePawn;
			default -> throw new IllegalStateException(
				"Unexpected symbol: " + symbol
			);
		};
	}

	public static final char[] SYMBOLS = {
		model.piece.rook.extension.BlackRook.SYMBOL,
		model.piece.knight.extension.BlackKnight.SYMBOL,
		model.piece.bishop.extension.BlackBishop.SYMBOL,
		model.piece.queen.extension.BlackQueen.SYMBOL,
		model.piece.king.extension.BlackKing.SYMBOL,
		model.piece.pawn.extension.BlackPawn.SYMBOL,
		model.piece.rook.extension.WhiteRook.SYMBOL,
		model.piece.knight.extension.WhiteKnight.SYMBOL,
		model.piece.bishop.extension.WhiteBishop.SYMBOL,
		model.piece.queen.extension.WhiteQueen.SYMBOL,
		model.piece.king.extension.WhiteKing.SYMBOL,
		model.piece.pawn.extension.WhitePawn.SYMBOL,
	};
}

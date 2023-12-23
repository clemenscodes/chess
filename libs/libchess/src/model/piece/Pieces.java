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
}

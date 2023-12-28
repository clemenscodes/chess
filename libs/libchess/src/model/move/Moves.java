package model.move;

import model.piece.Pieces;

public enum Moves {
	Quiet,
	DoublePawnPush,
	KingCastle,
	QueenCastle,
	Capture,
	EnPassantCapture,
	KnightPromotion,
	BishopPromotion,
	RookPromotion,
	QueenPromotion,
	KnightPromotionCapture,
	BishopPromotionCapture,
	RookPromotionCapture,
	QueenPromotionCapture;

	public static Moves getPromotionMove(Pieces piece) {
		return switch (piece) {
			case BlackRook, WhiteRook -> Moves.RookPromotion;
			case BlackKnight, WhiteKnight -> Moves.KnightPromotion;
			case BlackBishop, WhiteBishop -> Moves.BishopPromotion;
			case BlackQueen, WhiteQueen -> Moves.QueenPromotion;
			default -> throw new Error("Invalid promotion piece");
		};
	}
}

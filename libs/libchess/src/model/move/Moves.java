package model.move;

import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.piece.Pieces;

public enum Moves {
	Quiet,
	SinglePawnPush,
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

	public static boolean isPromotion(IBitboard destination, IBitboard promotionMask) {
		return Bitboard.overlap(destination, promotionMask);
	}

	public static boolean isCapture(IBitboard destination, IBoard board) {
		return Bitboard.overlap(destination, board.getOpponentPieces());
	}

	public static boolean isEnPassant(IBitboard destination, IBoard board) {
		return Bitboard.overlap(destination, board.getFen().getEnPassantMask());
	}
}

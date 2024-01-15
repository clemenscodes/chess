package model;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.fen.IForsythEdwardsNotation;
import model.piece.IPiece;

public enum State implements Serializable {
	Start,
	Playing,
	GameOver;

	public static boolean isCheckmate(IBoard board) {
		if (!isCheck(board)) {
			return false;
		}
		IForsythEdwardsNotation fen = board.getFen();
		IBitboard enemyAttack = board.getAllOpponentAttacks();
		System.out.println(enemyAttack);
		IPiece[] pieces = fen.isWhite() ? board.getAllWhitePieces() : board.getAllBlackPieces();
		return false;
	}

	private static boolean isCheck(IBoard board) {
		IBitboard king = board.getKing(board.getFen().isWhite());
		IBitboard enemyAttack = board.getAllOpponentAttacks();
		return Bitboard.overlap(king, enemyAttack);
	}
}

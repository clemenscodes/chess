package model;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

public enum State implements Serializable {
	Start,
	Playing,
	GameOver;

	public static boolean isCheckmate(IBoard board) {
		return isCheck(board) && hasNoLegalMoves(board);
	}

	public static boolean isStalemate(IBoard board) {
		return !isCheck(board) && hasNoLegalMoves(board);
	}

	private static boolean hasNoLegalMoves(IBoard board) {
		var moves = board.getAllMoves(board.getFen().isWhite());
		for (var move : moves) {
			try {
				return board
					.getPiece(move[0])
					.isInvalidMove(Square.getIndex(move[0]), Square.getIndex(move[1]), board);
			} catch (Error ignored) {}
		}
		return true;
	}

	private static boolean isCheck(IBoard board) {
		boolean isWhite = board.getFen().isWhite();
		IBitboard king = board.getKing(isWhite);
		IBitboard attacks = isWhite ? board.getAllOpponentAttacks() : board.getAllFriendlyAttacks();
		return Bitboard.overlap(king, attacks);
	}
}

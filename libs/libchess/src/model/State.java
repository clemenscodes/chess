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
		if (!isCheck(board)) {
			return false;
		}
		var moves = board.getAllMoves(board.getFen().isWhite());
		for (var move : moves) {
			try {
				return testMove(move, board);
			} catch (Error ignored) {}
		}
		return true;
	}

	private static boolean testMove(Square[] move, IBoard board) {
		IPiece piece = board.getPiece(move[0]);
		int source = Square.getIndex(move[0]);
		int destination = Square.getIndex(move[1]);
		return piece.isInvalidMove(source, destination, board);
	}

	private static boolean isCheck(IBoard board) {
		boolean isWhite = board.getFen().isWhite();
		IBitboard king = board.getKing(isWhite);
		IBitboard attacks = isWhite ? board.getAllOpponentAttacks() : board.getAllFriendlyAttacks();
		return Bitboard.overlap(king, attacks);
	}
}

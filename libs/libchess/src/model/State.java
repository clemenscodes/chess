package model;

import java.io.Serializable;
import java.util.ArrayList;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.fen.IForsythEdwardsNotation;
import model.piece.IPiece;

public enum State implements Serializable {
	Start,
	Playing,
	GameOver;

	public static boolean isCheckmate(IBoard board) {
		boolean mate = false;
		if (!isCheck(board)) {
			return false;
		}
		IForsythEdwardsNotation fen = board.getFen();
		boolean isWhite = fen.isWhite();
		ArrayList<Square[]> moves = board.getAllMoves(isWhite);
		for (var move : moves) {
			IPiece piece = board.getPiece(move[0]);
			int source = Square.getIndex(move[0]);
			int destination = Square.getIndex(move[1]);
			try {
				mate = piece.isInvalidMove(source, destination, board);
				if (!mate) {
					return false;
				}
			} catch (Error e) {
				mate = true;
			}
		}
		return mate;
	}

	private static boolean isCheck(IBoard board) {
		boolean isWhite = board.getFen().isWhite();
		IBitboard king = board.getKing(isWhite);
		IBitboard attacks = isWhite ? board.getAllOpponentAttacks() : board.getAllFriendlyAttacks();
		return Bitboard.overlap(king, attacks);
	}
}

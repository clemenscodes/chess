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
		if (!isCheck(board)) {
			return false;
		}
		IForsythEdwardsNotation fen = board.getFen();
		boolean isWhite = fen.isWhite();
		ArrayList<Square[]> destinations = board.getAllMoveDestinations(isWhite);
		for (var d : destinations) {
			System.out.println(d[0].name() + d[1].name());
			int source = Square.getIndex(d[0]);
			int destination = Square.getIndex(d[1]);
		}
		return false;
	}

	private static boolean isCheck(IBoard board) {
		boolean isWhite = board.getFen().isWhite();
		IBitboard king = board.getKing(isWhite);
		IBitboard attacks = isWhite ? board.getAllOpponentAttacks() : board.getAllFriendlyAttacks();
		return Bitboard.overlap(king, attacks);
	}
}

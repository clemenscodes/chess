package model.move;

import java.io.Serializable;
import model.board.IBoard;
import model.board.Square;
import model.piece.Pieces;

public class MoveList implements IMoveList, Serializable {

	public static final int MOVE_LIMIT = 17697;
	private int playedMoves;
	private final IMove[] moves;

	public MoveList() {
		playedMoves = 0;
		moves = new Move[MOVE_LIMIT];
	}

	public int getPlayedMoves() {
		return playedMoves;
	}

	public IMove[] getMoves() {
		return moves;
	}

	public void makeMove(IMove move, IBoard board) {
		if (playedMoves >= MOVE_LIMIT) {
			return;
		}
		Moves kind = getMoveKind(move, board);
		move(kind, move, board);
		moves[playedMoves++] = move;
	}

	public void unmakeMove(IBoard board) {
		if (playedMoves <= 0) {
			return;
		}
		moves[--playedMoves] = null;
	}

	private Moves getMoveKind(IMove move, IBoard board) {
		return Moves.Quiet;
	}

	private void move(Moves kind, IMove move, IBoard board) {
		switch (kind) {
			case Quiet -> quietMove(move, board);
			case DoublePawnPush -> doublePawnPush(move, board);
			case KingCastle -> kingCastle(move, board);
			case QueenCastle -> queenCastle(move, board);
			case Capture -> capture(move, board);
			case EnPassantCapture -> enPassantCapture(move, board);
			case KnightPromotion -> knightPromotion(move, board);
			case BishopPromotion -> bishopPromotion(move, board);
			case RookPromotion -> rookPromotion(move, board);
			case QueenPromotion -> queenPromotion(move, board);
			case KnightPromotionCapture -> knightPromotionCapture(move, board);
			case BishopPromotionCapture -> bishopPromotionCapture(move, board);
			case RookPromotionCapture -> rookPromotionCapture(move, board);
			case QueenPromotionCapture -> queenPromotionCapture(move, board);
		}
	}

	private void quietMove(IMove move, IBoard board) {
		int src = Square.getIndex(move.getSource());
		int dst = Square.getIndex(move.getDestination());
		Pieces piece = board.getPieceByIndex(src);
		switch (piece) {
			case WhitePawn -> board.getWhitePawn().move(src, dst, board);
			case WhiteBishop -> board.getWhiteBishop().move(src, dst, board);
			case WhiteKnight -> board.getWhiteKnight().move(src, dst, board);
			case WhiteRook -> board.getWhiteRook().move(src, dst, board);
			case WhiteQueen -> board.getWhiteQueen().move(src, dst, board);
			case WhiteKing -> board.getWhiteKing().move(src, dst, board);
			case BlackPawn -> board.getBlackPawn().move(src, dst, board);
			case BlackBishop -> board.getBlackBishop().move(src, dst, board);
			case BlackKnight -> board.getBlackKnight().move(src, dst, board);
			case BlackRook -> board.getBlackRook().move(src, dst, board);
			case BlackQueen -> board.getBlackQueen().move(src, dst, board);
			case BlackKing -> board.getBlackKing().move(src, dst, board);
		}
	}

	private IBoard doublePawnPush(IMove move, IBoard board) {
		return board;
	}

	private IBoard kingCastle(IMove move, IBoard board) {
		return board;
	}

	private IBoard queenCastle(IMove move, IBoard board) {
		return board;
	}

	private IBoard capture(IMove move, IBoard board) {
		return board;
	}

	private IBoard enPassantCapture(IMove move, IBoard board) {
		return board;
	}

	private IBoard knightPromotion(IMove move, IBoard board) {
		return board;
	}

	private IBoard bishopPromotion(IMove move, IBoard board) {
		return board;
	}

	private IBoard rookPromotion(IMove move, IBoard board) {
		return board;
	}

	private IBoard queenPromotion(IMove move, IBoard board) {
		return board;
	}

	private IBoard knightPromotionCapture(IMove move, IBoard board) {
		return board;
	}

	private IBoard bishopPromotionCapture(IMove move, IBoard board) {
		return board;
	}

	private IBoard rookPromotionCapture(IMove move, IBoard board) {
		return board;
	}

	private IBoard queenPromotionCapture(IMove move, IBoard board) {
		return board;
	}
}

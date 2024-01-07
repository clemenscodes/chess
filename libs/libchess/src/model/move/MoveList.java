package model.move;

import java.io.Serializable;
import model.board.IBoard;
import model.board.Square;
import model.piece.Pieces;
import model.reader.IReader;

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

	public void makeMove(Square source, Square destination, IBoard board, IReader reader) {
		if (getPlayedMoves() >= MOVE_LIMIT) {
			throw new Error("Making another move is impossible");
		}
		incrementPlayedMoves();
		moves[getNextMoveIndex()] = move(source, destination, board, reader);
	}

	private void incrementPlayedMoves() {
		setPlayedMoves(getPlayedMoves() + 1);
	}

	private int getNextMoveIndex() {
		return getPlayedMoves() - 1;
	}

	private void setPlayedMoves(int playedMoves) {
		if (playedMoves > MOVE_LIMIT) {
			throw new Error("Can not set played moves higher than the allowed maximum");
		}
		this.playedMoves = playedMoves;
	}

	private IMove move(Square source, Square destination, IBoard board, IReader reader) {
		int src = Square.getIndex(source);
		int dst = Square.getIndex(destination);
		Pieces piece = board.getPieceByIndex(src);
		return switch (piece) {
			case WhitePawn -> board.getWhitePawn().move(src, dst, board, reader);
			case WhiteBishop -> board.getWhiteBishop().move(src, dst, board);
			case WhiteKnight -> board.getWhiteKnight().move(src, dst, board);
			case WhiteRook -> board.getWhiteRook().move(src, dst, board);
			case WhiteQueen -> board.getWhiteQueen().move(src, dst, board);
			case WhiteKing -> board.getWhiteKing().move(src, dst, board);
			case BlackPawn -> board.getBlackPawn().move(src, dst, board, reader);
			case BlackBishop -> board.getBlackBishop().move(src, dst, board);
			case BlackKnight -> board.getBlackKnight().move(src, dst, board);
			case BlackRook -> board.getBlackRook().move(src, dst, board);
			case BlackQueen -> board.getBlackQueen().move(src, dst, board);
			case BlackKing -> board.getBlackKing().move(src, dst, board);
		};
	}

	@Override
	public String toString() {
		IMove[] moves = getMoves();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < moves.length; i++) {
			if (moves[i] == null) {
				continue;
			}
			if (i % 2 == 0) {
				stringBuilder.append((i / 2) + 1).append(". ");
			}
			stringBuilder.append(moves[i]).append(" ");
			stringBuilder.append(moves[i].getClass());
			if (i % 2 != 0) {
				stringBuilder.append("\n");
			}
		}
		return stringBuilder.toString();
	}
}

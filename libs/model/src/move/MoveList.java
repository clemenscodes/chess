package move;

import api.model.board.IBoard;
import api.model.board.Square;
import api.model.move.IMove;
import api.model.move.IMoveList;
import api.model.piece.Pieces;
import api.model.reader.IReader;
import board.Board;
import java.io.Serializable;
import java.util.ArrayList;

public class MoveList implements IMoveList, Serializable {

	private ArrayList<IMove> moves;

	public MoveList() {
		setMoves(new ArrayList<>());
	}

	public int getPlayedMoves() {
		return getMoves().size();
	}

	public void makeMove(Square source, Square destination, IBoard board, IReader reader) {
		validateMove(source, destination);
		performMove(source, destination, board, reader);
		updateBoardState(board);
	}

	private ArrayList<IMove> getMoves() {
		return moves;
	}

	private void validateMove(Square source, Square destination) {
		if (source == destination) {
			throw new IllegalArgumentException("Source and destination must be different");
		}
	}

	private void performMove(Square source, Square destination, IBoard board, IReader reader) {
		addMove(source, destination, board, reader);
		board.getFen().switchActiveColor();
	}

	private void updateBoardState(IBoard board) {
		board.getFen().updatePiecePlacementData(board);
	}

	private void addMove(Square source, Square destination, IBoard board, IReader reader) {
		moves.add(move(source, destination, board, reader));
	}

	private void setMoves(ArrayList<IMove> moves) {
		this.moves = moves;
	}

	private IMove move(Square source, Square destination, IBoard board, IReader reader) {
		int src = Board.getIndex(source);
		int dst = Board.getIndex(destination);
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
		ArrayList<IMove> moves = getMoves();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < moves.size(); i++) {
			if (i % 2 == 0) {
				stringBuilder.append((i / 2) + 1).append(". ");
				stringBuilder.append(moves.get(i));
			}
			if (i % 2 != 0) {
				stringBuilder.append(" ").append(moves.get(i)).append("\n");
			}
		}
		return stringBuilder.toString();
	}
}

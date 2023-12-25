package model.piece.pawn.extension;

import java.io.Serializable;
import java.util.Arrays;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.move.IMove;
import model.piece.Pieces;
import model.piece.pawn.Pawn;

public class WhitePawn extends Pawn implements Serializable {

	public static final char SYMBOL = '♙';

	public WhitePawn() {
		super(Pieces.WhitePawn);
	}

	public boolean isInvalidMove(int source, int destination, IBoard board) {
		return false;
	}

	public IBoard move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		generateMoves(board);
		System.out.print("Moving white pawn from ");
		System.out.print(Square.getSquare(source));
		System.out.print(" to ");
		System.out.println(Square.getSquare(destination));
		return board;
	}

	public IMove[] generateMoves(IBoard board) {
		System.out.print("[DEBUG] Generating moves for white pawns");
		IBitboard[] pushTargets = getPushTargets(board);
		System.out.println(Arrays.toString(pushTargets));
		return new IMove[0];
	}

	private IBitboard[] getPushTargets(IBoard board) {
		IBitboard emptySquares = board.getEmptySquares();
		IBitboard whitePawns = board.getWhitePawn().getBitboard();
		IBitboard singlePushTargets = getSinglePushTargets(whitePawns, emptySquares);
		IBitboard doublePushTargets = getDoublePushTargets(whitePawns, emptySquares);
		return new IBitboard[] { singlePushTargets, doublePushTargets };
	}

	private IBitboard getSinglePushTargets(IBitboard whitePawns, IBitboard emptySquares) {
		return Bitboard.intersect(Bitboard.shiftNorth(whitePawns), emptySquares);
	}

	private IBitboard getDoublePushTargets(IBitboard whitePawns, IBitboard emptySquares) {
		IBitboard singlePushTargets = getSinglePushTargets(whitePawns, emptySquares);
		IBitboard doublePushMask = Bitboard.intersect(emptySquares, Board.fourthRank);
		return Bitboard.intersect(Bitboard.shiftNorth(singlePushTargets), doublePushMask);
	}
}

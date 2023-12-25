package model.piece.pawn.extension;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.piece.Pieces;
import model.piece.pawn.Pawn;

public class WhitePawn extends Pawn implements Serializable {

	public static final char SYMBOL = '♙';

	public WhitePawn() {
		super(Pieces.WhitePawn);
	}

	public boolean isInvalidMove(int source, int destination, IBoard board) {
		if (!Bitboard.checkBit(getMovablePawns(board), source)) {
			System.err.println("Pawn on " + Square.getSquare(source) + " can not push");
			return true;
		}
		IBitboard pawn = Bitboard.getSingleBit(source);
		if (!Bitboard.checkBit(getTargets(pawn, board), destination)) {
			System.err.println("Can not push pawn to " + Square.getSquare(destination));
			return true;
		}
		return false;
	}

	public void move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		getBitboard().toggleBits(getMoveMask(source, destination));
	}

	private IBitboard getMovablePawns(IBoard board) {
		IBitboard pushablePawns = getPushablePawns(board.getEmptySquares());
		IBitboard attackingPawns = getAttackingPawns(board);
		return Bitboard.merge(pushablePawns, attackingPawns);
	}

	private IBitboard getAttackingPawns(IBoard board) {
		IBitboard blackPieces = board.getBlackPieces();
		return new Bitboard();
	}

	private IBitboard getTargets(IBitboard pawn, IBoard board) {
		IBitboard pushTargets = getPushTargets(pawn, board.getEmptySquares());
		IBitboard attackTargets = getAttackTargets(board);
		return Bitboard.merge(pushTargets, attackTargets);
	}

	private IBitboard getAttackTargets(IBoard board) {
		IBitboard blackPieces = board.getBlackPieces();
		return new Bitboard();
	}

	private IBitboard getPushTargets(IBitboard pawn, IBitboard emptySquares) {
		IBitboard singlePushTargets = getSinglePushTargets(pawn, emptySquares);
		IBitboard doublePushTargets = getDoublePushTargets(pawn, emptySquares);
		return Bitboard.merge(singlePushTargets, doublePushTargets);
	}

	private IBitboard getSinglePushTargets(IBitboard pawn, IBitboard emptySquares) {
		return Bitboard.intersect(Bitboard.shiftNorth(pawn), emptySquares);
	}

	private IBitboard getDoublePushTargets(IBitboard pawn, IBitboard emptySquares) {
		IBitboard singlePushTargets = getSinglePushTargets(pawn, emptySquares);
		IBitboard doublePushMask = Bitboard.intersect(emptySquares, Board.fourthRank);
		return Bitboard.intersect(Bitboard.shiftNorth(singlePushTargets), doublePushMask);
	}

	private IBitboard getPushablePawns(IBitboard emptySquares) {
		IBitboard singlePushablePawns = getSinglePushablePawns(emptySquares);
		IBitboard doublePushablePawns = getDoublePushablePawns(emptySquares);
		return Bitboard.merge(singlePushablePawns, doublePushablePawns);
	}

	private IBitboard getSinglePushablePawns(IBitboard emptySquares) {
		return Bitboard.intersect(Bitboard.shiftSouth(emptySquares), getBitboard());
	}

	private IBitboard getDoublePushablePawns(IBitboard emptySquares) {
		IBitboard thirdRank = Bitboard.shiftSouth(Bitboard.intersect(emptySquares, Board.fourthRank));
		IBitboard emptyThirdRank = Bitboard.intersect(thirdRank, emptySquares);
		return getSinglePushablePawns(emptyThirdRank);
	}
}

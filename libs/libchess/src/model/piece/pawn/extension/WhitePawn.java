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
		IBitboard emptySquares = board.getEmptySquares();
		if (!Bitboard.checkBit(getPushablePawns(emptySquares), source)) {
			System.err.println("Pawn on " + Square.getSquare(source) + " can not push");
			return true;
		}
		IBitboard pawn = Bitboard.getSingleBit(source);
		if (!Bitboard.checkBit(getPushTargets(pawn, emptySquares), destination)) {
			System.err.println("Can not push pawn to " + Square.getSquare(destination));
			return true;
		}
		return false;
	}

	public void move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		System.out.print("Moving white pawn from ");
		System.out.print(Square.getSquare(source));
		System.out.print(" to ");
		System.out.println(Square.getSquare(destination));
		getBitboard().toggleBits(getMoveMask(source, destination));
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

	private IBitboard getSinglePushablePawns(IBitboard emptySquares) {
		return Bitboard.intersect(Bitboard.shiftSouth(emptySquares), getBitboard());
	}

	private IBitboard getDoublePushablePawns(IBitboard emptySquares) {
		IBitboard thirdRank = Bitboard.shiftSouth(Bitboard.intersect(emptySquares, Board.fourthRank));
		IBitboard emptyThirdRank = Bitboard.intersect(thirdRank, emptySquares);
		return getSinglePushablePawns(emptyThirdRank);
	}

	private IBitboard getPushablePawns(IBitboard emptySquares) {
		IBitboard singlePushablePawns = getSinglePushablePawns(emptySquares);
		IBitboard doublePushablePawns = getDoublePushablePawns(emptySquares);
		return Bitboard.merge(singlePushablePawns, doublePushablePawns);
	}
}

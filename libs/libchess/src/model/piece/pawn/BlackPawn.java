package model.piece.pawn;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.piece.Pieces;

public class BlackPawn extends Pawn implements Serializable {

	public static final char SYMBOL = '♟';

	public BlackPawn() {
		super(Pieces.BlackPawn);
	}

	public BlackPawn(IBitboard board) {
		super(Pieces.BlackPawn, board);
	}

	protected IBitboard getWestAttacks(IBitboard pawns) {
		IBitboard westAttackMask = Board.notLastFile;
		IBitboard westAttack = Bitboard.shiftSouthWest(pawns);
		return Bitboard.intersect(westAttack, westAttackMask);
	}

	protected IBitboard getEastAttacks(IBitboard pawns) {
		IBitboard eastAttackMask = Board.notFirstFile;
		IBitboard eastAttack = Bitboard.shiftSouthEast(pawns);
		return Bitboard.intersect(eastAttack, eastAttackMask);
	}

	protected IBitboard getAttackingPawns(IBoard board) {
		IBitboard attacks = getAttacks(getBitboard(), board);
		IBitboard westAttackingPawns = Bitboard.shiftNorthWest(attacks);
		IBitboard eastAttackingPawns = Bitboard.shiftNorthEast(attacks);
		return Bitboard.merge(westAttackingPawns, eastAttackingPawns);
	}

	protected IBitboard getSinglePushTargets(IBitboard pawn, IBitboard emptySquares) {
		IBitboard pushTargets = Bitboard.shiftSouth(pawn);
		return Bitboard.intersect(pushTargets, emptySquares);
	}

	protected IBitboard getDoublePushTargets(IBitboard pawn, IBitboard emptySquares) {
		IBitboard singlePushTargets = getSinglePushTargets(pawn, emptySquares);
		IBitboard skippedRank = Board.fifthRank;
		IBitboard doublePushMask = Bitboard.intersect(emptySquares, skippedRank);
		IBitboard doublePushTargets = Bitboard.shiftSouth(singlePushTargets);
		return Bitboard.intersect(doublePushTargets, doublePushMask);
	}

	protected IBitboard getSinglePushablePawns(IBitboard emptySquares) {
		IBitboard singlePushablePawns = Bitboard.shiftNorth(emptySquares);
		return Bitboard.intersect(singlePushablePawns, getBitboard());
	}

	protected IBitboard getDoublePushablePawns(IBitboard emptySquares) {
		IBitboard skippedRank = Bitboard.shiftNorth(
			Bitboard.intersect(emptySquares, Board.fifthRank)
		);
		IBitboard emptySkippedRank = Bitboard.intersect(skippedRank, emptySquares);
		return getSinglePushablePawns(emptySkippedRank);
	}

	protected Pieces[] getPromotionPieces() {
		return Pieces.getBlackPromotionPieces();
	}
}

package model.piece.pawn;

import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.piece.Piece;
import model.piece.Pieces;

public class WhitePawn extends Pawn {

	public WhitePawn() {
		super(Pieces.WhitePawn);
	}

	public WhitePawn(IBitboard board) {
		super(Pieces.WhitePawn, board);
	}

	protected IBitboard getWestAttacks(IBitboard pawns) {
		IBitboard westAttackMask = Board.notLastFile;
		IBitboard westAttack = Bitboard.shiftNorthWest(pawns);
		return Bitboard.intersect(westAttack, westAttackMask);
	}

	protected IBitboard getEastAttacks(IBitboard pawns) {
		IBitboard eastAttackMask = Board.notFirstFile;
		IBitboard eastAttack = Bitboard.shiftNorthEast(pawns);
		return Bitboard.intersect(eastAttack, eastAttackMask);
	}

	protected IBitboard getAttackingPawns(IBoard board) {
		IBitboard attacks = getAttacks(getBitboard(), board);
		IBitboard westAttackingPawns = Bitboard.shiftSouthEast(attacks);
		IBitboard eastAttackingPawns = Bitboard.shiftSouthWest(attacks);
		return Bitboard.merge(westAttackingPawns, eastAttackingPawns);
	}

	protected IBitboard getSinglePushTargets(IBitboard pawn, IBitboard emptySquares) {
		IBitboard pushTargets = Bitboard.shiftNorth(pawn);
		return Bitboard.intersect(pushTargets, emptySquares);
	}

	protected IBitboard getDoublePushTargets(IBitboard pawn, IBitboard emptySquares) {
		IBitboard singlePushTargets = getSinglePushTargets(pawn, emptySquares);
		IBitboard skippedRank = Board.fourthRank;
		IBitboard doublePushMask = Bitboard.intersect(emptySquares, skippedRank);
		IBitboard doublePushTargets = Bitboard.shiftNorth(singlePushTargets);
		return Bitboard.intersect(doublePushTargets, doublePushMask);
	}

	protected IBitboard getSinglePushablePawns(IBitboard emptySquares) {
		IBitboard singlePushablePawns = Bitboard.shiftSouth(emptySquares);
		return Bitboard.intersect(singlePushablePawns, getBitboard());
	}

	protected IBitboard getDoublePushablePawns(IBitboard emptySquares) {
		IBitboard skippedRank = Bitboard.shiftSouth(
			Bitboard.intersect(emptySquares, Board.fourthRank)
		);
		IBitboard emptySkippedRank = Bitboard.intersect(skippedRank, emptySquares);
		return getSinglePushablePawns(emptySkippedRank);
	}

	protected Pieces[] getPromotionPieces() {
		return Piece.getWhitePromotionPieces();
	}
}

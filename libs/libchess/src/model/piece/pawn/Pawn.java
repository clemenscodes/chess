package model.piece.pawn;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.move.Moves;
import model.piece.MovableWithReader;
import model.piece.Piece;
import model.piece.Pieces;
import model.piece.pawn.extension.WhitePawn;
import model.reader.IReader;

public abstract class Pawn extends Piece implements MovableWithReader, Serializable {

	public Pawn(Pieces variant) {
		super(variant);
	}

	public boolean isInvalidMove(int source, int destination, IBoard board) {
		if (!Bitboard.checkBit(getMovablePawns(board), source)) {
			System.err.println("Pawn on " + Square.getSquare(source) + " can not push");
			return true;
		}
		IBitboard pawn = Bitboard.getSingleBit(source);
		System.out.println(getTargets(pawn, board));
		if (!Bitboard.checkBit(getTargets(pawn, board), destination)) {
			System.err.println("Can not push pawn to " + Square.getSquare(destination));
			return true;
		}
		return false;
	}

	public Moves move(int source, int destination, IBoard board, IReader reader) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		IBitboard destinationBit = Bitboard.getSingleBit(destination);
		IBitboard promotionMask = this instanceof WhitePawn ? Board.eighthRank : Board.firstRank;
		if (Bitboard.overlap(destinationBit, promotionMask)) {
			return promotePawn(board, Bitboard.getSingleBit(source), destinationBit, reader);
		}
		if (Bitboard.overlap(destinationBit, getOpponentPieces(board))) {
			return captureMove(source, destination, board);
		}
		return quietMove(source, destination, board);
	}

	private Moves captureMove(int source, int destination, IBoard board) {
		var fen = board.getFen();
		fen.resetHalfMoveClock();
		fen.incrementFullMoveNumber();
		Pieces piece = this instanceof WhitePawn ? Pieces.BlackPawn : Pieces.WhitePawn;
		board.getPiece(piece).getBitboard().toggleBits(Bitboard.getSingleBit(destination));
		getBitboard().toggleBits(getMoveMask(source, destination));
		return Moves.Capture;
	}

	private Moves quietMove(int source, int destination, IBoard board) {
		System.out.println("Quiet!");
		var fen = board.getFen();
		fen.incrementHalfMoveClock();
		fen.incrementFullMoveNumber();
		getBitboard().toggleBits(getMoveMask(source, destination));
		return Moves.Quiet;
	}

	public IBitboard getAttacks(IBoard board) {
		IBitboard pawns = getBitboard();
		IBitboard maskedWestAttacks = getWestAttacks(pawns);
		IBitboard maskedEastAttacks = getEastAttacks(pawns);
		IBitboard attacks = Bitboard.merge(maskedWestAttacks, maskedEastAttacks);
		IBitboard opponentPieces = getOpponentPieces(board);
		return Bitboard.intersect(attacks, opponentPieces);
	}

	private IBitboard getWestAttacks(IBitboard pawns) {
		IBitboard westAttackMask = this instanceof WhitePawn
			? Board.notLastFile
			: Board.notFirstFile;
		IBitboard westAttack = this instanceof WhitePawn
			? Bitboard.shiftNorthWest(pawns)
			: Bitboard.shiftSouthWest(pawns);
		return Bitboard.intersect(westAttack, westAttackMask);
	}

	private IBitboard getEastAttacks(IBitboard pawns) {
		IBitboard eastAttackMask = this instanceof WhitePawn
			? Board.notFirstFile
			: Board.notLastFile;
		IBitboard eastAttack = this instanceof WhitePawn
			? Bitboard.shiftNorthEast(pawns)
			: Bitboard.shiftSouthEast(pawns);
		return Bitboard.intersect(eastAttack, eastAttackMask);
	}

	private Moves promotePawn(
		IBoard board,
		IBitboard sourceBit,
		IBitboard destinationBit,
		IReader reader
	) {
		System.out.println(
			"Pawn promotion! Select the piece you want: Q (Queen), R (Rook), N (Knight), B (Bishop)"
		);
		Pieces piece = Pieces.getSelectedPiece(getPromotionPieces(), getSelection(reader));
		board.getPiece(piece).getBitboard().merge(destinationBit);
		getBitboard().toggleBits(sourceBit);
		return Moves.getPromotionMove(piece);
	}

	private String getSelection(IReader reader) {
		String userInput = reader.readLine();
		while (!userInput.matches("[QRNB]")) {
			System.err.println("Invalid selection");
			userInput = reader.readLine();
		}
		return userInput;
	}

	private Pieces[] getPromotionPieces() {
		return this instanceof model.piece.pawn.extension.WhitePawn
			? Pieces.getWhitePromotionPieces()
			: Pieces.getBlackPromotionPieces();
	}

	private IBitboard getMovablePawns(IBoard board) {
		IBitboard pushablePawns = getPushablePawns(board.getEmptySquares());
		IBitboard attackingPawns = getAttackingPawns(board);
		return Bitboard.merge(pushablePawns, attackingPawns);
	}

	private IBitboard getOpponentPieces(IBoard board) {
		return this instanceof WhitePawn ? board.getBlackPieces() : board.getWhitePieces();
	}

	private IBitboard getAttackingPawns(IBoard board) {
		IBitboard attacks = getAttacks(board);
		IBitboard westAttackingPawns = this instanceof WhitePawn
			? Bitboard.shiftSouthEast(attacks)
			: Bitboard.shiftNorthWest(attacks);
		IBitboard eastAttackingPawns = this instanceof WhitePawn
			? Bitboard.shiftSouthWest(attacks)
			: Bitboard.shiftNorthEast(attacks);
		return Bitboard.merge(westAttackingPawns, eastAttackingPawns);
	}

	private IBitboard getTargets(IBitboard pawn, IBoard board) {
		IBitboard pushTargets = getPushTargets(pawn, board.getEmptySquares());
		IBitboard attackTargets = getAttacks(board);
		return Bitboard.merge(pushTargets, attackTargets);
	}

	private IBitboard getPushTargets(IBitboard pawn, IBitboard emptySquares) {
		IBitboard singlePushTargets = getSinglePushTargets(pawn, emptySquares);
		IBitboard doublePushTargets = getDoublePushTargets(pawn, emptySquares);
		return Bitboard.merge(singlePushTargets, doublePushTargets);
	}

	private IBitboard getSinglePushTargets(IBitboard pawn, IBitboard emptySquares) {
		IBitboard pushTargets = this instanceof WhitePawn
			? Bitboard.shiftNorth(pawn)
			: Bitboard.shiftSouth(pawn);
		return Bitboard.intersect(pushTargets, emptySquares);
	}

	private IBitboard getDoublePushTargets(IBitboard pawn, IBitboard emptySquares) {
		IBitboard singlePushTargets = getSinglePushTargets(pawn, emptySquares);
		IBitboard skippedRank = this instanceof WhitePawn ? Board.fourthRank : Board.fifthRank;
		IBitboard doublePushMask = Bitboard.intersect(emptySquares, skippedRank);
		IBitboard doublePushTargets = this instanceof WhitePawn
			? Bitboard.shiftNorth(singlePushTargets)
			: Bitboard.shiftSouth(singlePushTargets);
		return Bitboard.intersect(doublePushTargets, doublePushMask);
	}

	private IBitboard getPushablePawns(IBitboard emptySquares) {
		IBitboard singlePushablePawns = getSinglePushablePawns(emptySquares);
		IBitboard doublePushablePawns = getDoublePushablePawns(emptySquares);
		return Bitboard.merge(singlePushablePawns, doublePushablePawns);
	}

	private IBitboard getSinglePushablePawns(IBitboard emptySquares) {
		IBitboard singlePushablePawns = this instanceof WhitePawn
			? Bitboard.shiftSouth(emptySquares)
			: Bitboard.shiftNorth(emptySquares);
		return Bitboard.intersect(singlePushablePawns, getBitboard());
	}

	private IBitboard getDoublePushablePawns(IBitboard emptySquares) {
		IBitboard skippedRank = this instanceof WhitePawn
			? Bitboard.shiftSouth(Bitboard.intersect(emptySquares, Board.fourthRank))
			: Bitboard.shiftNorth(Bitboard.intersect(emptySquares, Board.fifthRank));
		IBitboard emptySkippedRank = Bitboard.intersect(skippedRank, emptySquares);
		return getSinglePushablePawns(emptySkippedRank);
	}
}

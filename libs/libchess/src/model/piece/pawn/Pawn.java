package model.piece.pawn;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.piece.MovableWithReader;
import model.piece.Piece;
import model.piece.Pieces;
import model.piece.pawn.extension.WhitePawn;
import model.util.io.reader.*;

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
		if (!Bitboard.checkBit(getTargets(pawn, board), destination)) {
			System.err.println("Can not push pawn to " + Square.getSquare(destination));
			return true;
		}
		return false;
	}

	public void move(int source, int destination, IBoard board, IReader reader) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		IBitboard destinationBit = Bitboard.getSingleBit(destination);
		IBitboard promotionMask = this instanceof WhitePawn ? Board.eighthRank : Board.firstRank;
		if (Bitboard.overlap(destinationBit, promotionMask)) {
			promotePawn(board, Bitboard.getSingleBit(source), destinationBit, reader);
		} else {
			getBitboard().toggleBits(getMoveMask(source, destination));
		}
	}

	private void promotePawn(IBoard board, IBitboard sourceBit, IBitboard destinationBit, IReader reader) {
		System.out.println("Pawn promotion! Select the piece you want: Q (Queen), R (Rook), N (Knight), B (Bishop)");
		board.getPiece(getSelectedPiece(getSelection(reader))).getBitboard().merge(destinationBit);
		getBitboard().toggleBits(sourceBit);
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
		return this instanceof WhitePawn ? getWhitePromotionPieces() : getBlackPromotionPieces();
	}

	private Pieces[] getWhitePromotionPieces() {
		return new Pieces[] { Pieces.WhiteQueen, Pieces.WhiteRook, Pieces.WhiteKnight, Pieces.WhiteBishop };
	}

	private Pieces[] getBlackPromotionPieces() {
		return new Pieces[] { Pieces.BlackQueen, Pieces.BlackRook, Pieces.BlackKnight, Pieces.BlackBishop };
	}

	private Pieces getSelectedPiece(String userInput) {
		Pieces[] pieces = getPromotionPieces();
		return switch (userInput) {
			case "Q" -> pieces[0];
			case "R" -> pieces[1];
			case "N" -> pieces[2];
			case "B" -> pieces[3];
			default -> throw new Error("Invalid input");
		};
	}

	private IBitboard getMovablePawns(IBoard board) {
		IBitboard pushablePawns = getPushablePawns(board.getEmptySquares());
		IBitboard attackingPawns = getAttackingPawns(board);
		return Bitboard.merge(pushablePawns, attackingPawns);
	}

	private IBitboard getAttackingPawns(IBoard board) {
		IBitboard opponentPieces = this instanceof WhitePawn ? board.getBlackPieces() : board.getWhitePieces();
		return new Bitboard();
	}

	private IBitboard getTargets(IBitboard pawn, IBoard board) {
		IBitboard pushTargets = getPushTargets(pawn, board.getEmptySquares());
		IBitboard attackTargets = getAttackTargets(board);
		return Bitboard.merge(pushTargets, attackTargets);
	}

	private IBitboard getAttackTargets(IBoard board) {
		IBitboard opponentPieces = this instanceof WhitePawn ? board.getBlackPieces() : board.getWhitePieces();
		return new Bitboard();
	}

	private IBitboard getPushTargets(IBitboard pawn, IBitboard emptySquares) {
		IBitboard singlePushTargets = getSinglePushTargets(pawn, emptySquares);
		IBitboard doublePushTargets = getDoublePushTargets(pawn, emptySquares);
		return Bitboard.merge(singlePushTargets, doublePushTargets);
	}

	private IBitboard getSinglePushTargets(IBitboard pawn, IBitboard emptySquares) {
		IBitboard pushTargets = this instanceof WhitePawn ? Bitboard.shiftNorth(pawn) : Bitboard.shiftSouth(pawn);
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
		IBitboard singlePushablePawns = this instanceof WhitePawn ? Bitboard.shiftSouth(emptySquares) : Bitboard.shiftNorth(emptySquares);
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

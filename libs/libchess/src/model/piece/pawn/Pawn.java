package model.piece.pawn;

import java.io.IOException;
import java.io.Serializable;
import model.bits.*;
import model.board.*;
import model.move.*;
import model.move.irreversible.pawn.*;
import model.move.irreversible.pawn.promotion.*;
import model.move.irreversible.pawn.promotion.capturing.*;
import model.piece.*;
import model.reader.IReader;

public abstract class Pawn extends Piece implements MovableWithReader, Serializable {

	public Pawn(Pieces variant) {
		super(variant);
	}

	public boolean isInvalidMove(int source, int destination, IBoard board, IReader reader) {
		return !(
			sourceSquareHasPiece(source, board) &&
			pieceCanMoveToDestination(source, destination, board) &&
			kingSafe(source, destination, board, reader)
		);
	}

	public IMove move(int source, int destination, IBoard board, IReader reader) {
		if (isInvalidMove(source, destination, board, reader)) {
			throw new Error("Invalid move");
		}
		return unsafeMove(source, destination, board, reader);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		IBitboard enPassantMask = board.getFen().getEnPassantMask();
		IBitboard maskedWestAttacks = getWestAttacks(piece);
		IBitboard maskedEastAttacks = getEastAttacks(piece);
		IBitboard regularAttacks = Bitboard.merge(maskedWestAttacks, maskedEastAttacks);
		IBitboard directAttacks = Bitboard.intersect(regularAttacks, board.getOpponentPieces());
		if (Bitboard.overlap(regularAttacks, enPassantMask)) {
			directAttacks.merge(enPassantMask);
		}
		return directAttacks;
	}

	@Override
	protected boolean pieceCanMoveToDestination(int source, int destination, IBoard board) {
		return Bitboard.checkBit(getTargets(Bitboard.getSingleBit(source), board), destination);
	}

	private boolean sourceSquareHasPiece(int source, IBoard board) {
		return Bitboard.checkBit(getMovablePieces(board), source);
	}

	@Override
	public IBitboard getAllAttacks(IBoard board) {
		return getAttacks(getBitboard(), board);
	}

	private IBoard simulateMove(int source, int destination, IBoard board, IReader reader) {
		try {
			IBoard copiedBoard = board.deepCopy();
			unsafeMove(source, destination, copiedBoard, reader);
			return copiedBoard;
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean kingSafe(int source, int destination, IBoard board, IReader reader) {
		IBoard simulatedBoard = simulateMove(source, destination, board, reader);
		boolean kingSafety = !Bitboard.overlap(
			board.getOwnKing(),
			simulatedBoard.getAllFriendlyAttacks()
		);
		if (!kingSafety) {
			throw new Error("King is in check");
		}
		return true;
	}

	private IBitboard getMovablePieces(IBoard board) {
		IBitboard pushablePawns = getPushablePawns(board.getEmptySquares());
		IBitboard attackingPawns = getAttackingPawns(board);
		return Bitboard.merge(pushablePawns, attackingPawns);
	}

	private IBitboard getTargets(IBitboard piece, IBoard board) {
		IBitboard pushTargets = getPushTargets(piece, board.getEmptySquares());
		IBitboard attackTargets = getAttacks(piece, board);
		return Bitboard.merge(pushTargets, attackTargets);
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

	private IBitboard getAttackingPawns(IBoard board) {
		IBitboard attacks = getAttacks(getBitboard(), board);
		IBitboard westAttackingPawns = this instanceof WhitePawn
			? Bitboard.shiftSouthEast(attacks)
			: Bitboard.shiftNorthWest(attacks);
		IBitboard eastAttackingPawns = this instanceof WhitePawn
			? Bitboard.shiftSouthWest(attacks)
			: Bitboard.shiftNorthEast(attacks);
		return Bitboard.merge(westAttackingPawns, eastAttackingPawns);
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

	private IMove pawnPush(Square source, Square destination, IBoard board) {
		IPiece pawn = board.getPiece(source);
		int moveIndexDifference = Math.abs(Square.getIndex(destination) - Square.getIndex(source));
		return (moveIndexDifference == Board.SIZE)
			? new SinglePawnPushMove(source, destination, board, pawn)
			: new DoublePawnPushMove(source, destination, board, pawn);
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
		return this instanceof WhitePawn
			? Pieces.getWhitePromotionPieces()
			: Pieces.getBlackPromotionPieces();
	}

	private IMove promotePawn(Square src, Square dst, IBoard board, IReader reader) {
		System.out.println(
			"Pawn promotion! Select the piece you want: Q (Queen), R (Rook), N (Knight), B (Bishop)"
		);
		Pieces piece = Pieces.getSelectedPiece(getPromotionPieces(), getSelection(reader));
		IBitboard destinationBit = Bitboard.getSingleBit(Square.getIndex(dst));
		return Move.isCapture(destinationBit, board)
			? makePromotionCapture(src, dst, piece, board)
			: makePromotion(src, dst, piece, board);
	}

	private IMove makePromotion(Square src, Square dst, Pieces piece, IBoard board) {
		Pieces[] pieces = getPromotionPieces();
		if (piece == pieces[0]) {
			return new QueenPromotionMove(src, dst, board, piece);
		}
		if (piece == pieces[1]) {
			return new RookPromotionMove(src, dst, board, piece);
		}
		if (piece == pieces[2]) {
			return new KnightPromotionMove(src, dst, board, piece);
		}
		return new BishopPromotionMove(src, dst, board, piece);
	}

	private IMove makePromotionCapture(Square src, Square dst, Pieces piece, IBoard board) {
		Pieces[] pieces = getPromotionPieces();
		if (piece == pieces[0]) {
			return new QueenPromotionCaptureMove(src, dst, board, piece);
		}
		if (piece == pieces[1]) {
			return new RookPromotionCaptureMove(src, dst, board, piece);
		}
		if (piece == pieces[2]) {
			return new KnightPromotionCaptureMove(src, dst, board, piece);
		}
		return new BishopPromotionCaptureMove(src, dst, board, piece);
	}

	private IMove unsafeMove(int source, int destination, IBoard board, IReader reader) {
		Square src = Square.getSquare(source);
		Square dst = Square.getSquare(destination);
		IBitboard destinationBit = Bitboard.getSingleBit(destination);
		IBitboard promotionMask = this instanceof WhitePawn ? Board.eighthRank : Board.firstRank;
		IPiece pawn = board.getPiece(Square.getSquare(source));
		if (Move.isPromotion(destinationBit, promotionMask)) {
			return promotePawn(src, dst, board, reader);
		}
		if (Move.isEnPassant(destinationBit, board)) {
			return new EnPassantCaptureMove(src, dst, board, pawn);
		}
		if (Move.isCapture(destinationBit, board)) {
			return new PawnCaptureMove(src, dst, board, pawn);
		}
		return pawnPush(src, dst, board);
	}
}

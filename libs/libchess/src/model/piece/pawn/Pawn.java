package model.piece.pawn;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.move.IMove;
import model.move.Moves;
import model.move.irreversible.capturing.CaptureMove;
import model.move.irreversible.pawn.promotion.BishopPromotionMove;
import model.move.irreversible.pawn.promotion.KnightPromotionMove;
import model.move.irreversible.pawn.promotion.QueenPromotionMove;
import model.move.irreversible.pawn.promotion.RookPromotionMove;
import model.move.irreversible.pawn.promotion.capturing.BishopPromotionCaptureMove;
import model.move.irreversible.pawn.promotion.capturing.KnightPromotionCaptureMove;
import model.move.irreversible.pawn.promotion.capturing.QueenPromotionCaptureMove;
import model.move.irreversible.pawn.promotion.capturing.RookPromotionCaptureMove;
import model.move.reversible.QuietMove;
import model.piece.MovableWithReader;
import model.piece.Piece;
import model.piece.Pieces;
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

	public IMove move(int source, int destination, IBoard board, IReader reader) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		IBitboard destinationBit = Bitboard.getSingleBit(destination);
		IBitboard promotionMask = this instanceof WhitePawn ? Board.eighthRank : Board.firstRank;
		if (Moves.isPromotion(destinationBit, promotionMask)) {
			return promotePawn(source, destination, board, reader);
		}
		if (Moves.isCapture(destinationBit, board.getOpponentPieces())) {
			return new CaptureMove(
				Square.getSquare(source),
				Square.getSquare(destination),
				board,
				this
			);
		}
		return new QuietMove(Square.getSquare(source), Square.getSquare(destination), board, this);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		IBitboard maskedWestAttacks = getWestAttacks(piece);
		IBitboard maskedEastAttacks = getEastAttacks(piece);
		IBitboard attacks = Bitboard.merge(maskedWestAttacks, maskedEastAttacks);
		return Bitboard.intersect(attacks, board.getOpponentPieces());
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

	private IBitboard getMovablePawns(IBoard board) {
		IBitboard pushablePawns = getPushablePawns(board.getEmptySquares());
		IBitboard attackingPawns = getAttackingPawns(board);
		return Bitboard.merge(pushablePawns, attackingPawns);
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

	private IBitboard getTargets(IBitboard pawn, IBoard board) {
		IBitboard pushTargets = getPushTargets(pawn, board.getEmptySquares());
		IBitboard attackTargets = getAttacks(pawn, board);
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

	private IMove promotePawn(int source, int destination, IBoard board, IReader reader) {
		System.out.println(
			"Pawn promotion! Select the piece you want: Q (Queen), R (Rook), N (Knight), B (Bishop)"
		);
		Pieces piece = Pieces.getSelectedPiece(getPromotionPieces(), getSelection(reader));
		return Moves.isCapture(Bitboard.getSingleBit(destination), board.getOpponentPieces())
			? makePromotionCapture(source, destination, piece, board)
			: makePromotion(source, destination, piece, board);
	}

	private IMove makePromotion(int source, int destination, Pieces piece, IBoard board) {
		Pieces[] pieces = getPromotionPieces();
		if (piece == pieces[0]) {
			return new QueenPromotionMove(
				Square.getSquare(source),
				Square.getSquare(destination),
				board,
				getBitboard(),
				piece
			);
		}
		if (piece == pieces[1]) {
			return new RookPromotionMove(
				Square.getSquare(source),
				Square.getSquare(destination),
				board,
				getBitboard(),
				piece
			);
		}
		if (piece == pieces[2]) {
			return new KnightPromotionMove(
				Square.getSquare(source),
				Square.getSquare(destination),
				board,
				getBitboard(),
				piece
			);
		}
		return new BishopPromotionMove(
			Square.getSquare(source),
			Square.getSquare(destination),
			board,
			getBitboard(),
			piece
		);
	}

	private IMove makePromotionCapture(int source, int destination, Pieces piece, IBoard board) {
		Pieces[] pieces = getPromotionPieces();
		if (piece == pieces[0]) {
			return new QueenPromotionCaptureMove(
				Square.getSquare(source),
				Square.getSquare(destination),
				board,
				getBitboard(),
				piece
			);
		}
		if (piece == pieces[1]) {
			return new RookPromotionCaptureMove(
				Square.getSquare(source),
				Square.getSquare(destination),
				board,
				getBitboard(),
				piece
			);
		}
		if (piece == pieces[2]) {
			return new KnightPromotionCaptureMove(
				Square.getSquare(source),
				Square.getSquare(destination),
				board,
				getBitboard(),
				piece
			);
		}
		return new BishopPromotionCaptureMove(
			Square.getSquare(source),
			Square.getSquare(destination),
			board,
			getBitboard(),
			piece
		);
	}
}

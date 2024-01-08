package model.piece;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.move.IMove;
import model.move.Moves;
import model.move.irreversible.capturing.CaptureMove;
import model.move.reversible.QuietMove;

public abstract class Piece implements IPiece, Serializable {

	private Pieces variant;
	private IBitboard bitboard;

	public Piece(Pieces variant) {
		setVariant(variant);
		setBitboard(new Bitboard());
	}

	public Pieces getVariant() {
		return variant;
	}

	public IBitboard getBitboard() {
		return bitboard;
	}

	public void setBitboard(IBitboard bitboard) {
		this.bitboard = bitboard;
	}

	public IBitboard getMoveMask(int source, int destination) {
		return Bitboard.merge(Bitboard.getSingleBit(source), Bitboard.getSingleBit(destination));
	}

	public IBitboard getAllAttacks(IBoard board) {
		return getAttacks(getBitboard(), board);
	}

	public IMove move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		Square src = Square.getSquare(source);
		Square dst = Square.getSquare(destination);
		if (Moves.isCapture(Bitboard.getSingleBit(destination), board)) {
			return new CaptureMove(src, dst, board, this);
		}
		return new QuietMove(src, dst, board, this);
	}

	protected IBitboard removeFriendlyPieces(IBitboard piece, IBoard board) {
		return Bitboard.intersect(piece, Bitboard.negate(board.getFriendlyPieces()));
	}

	protected boolean isInvalidMove(int source, int destination, IBoard board) {
		return !(
			Bitboard.checkBit(getBitboard(), source) &&
			Bitboard.checkBit(getAttacks(Bitboard.getSingleBit(source), board), destination)
		);
	}

	protected IBitboard getNorthWestRay(IBitboard piece, IBitboard occupiedSquares) {
		IBitboard northWestRay = new Bitboard();
		IBitboard northWestShift = Bitboard.shiftNorthWest(piece);
		while (
			canSlideWest(northWestRay, northWestShift, occupiedSquares) &&
			!Bitboard.overlap(piece, Board.eighthRank)
		) {
			northWestRay.merge(northWestShift);
			northWestShift = Bitboard.shiftNorthWest(northWestShift);
		}
		return northWestRay;
	}

	protected IBitboard getNorthEastRay(IBitboard piece, IBitboard occupiedSquares) {
		IBitboard northEastRay = new Bitboard();
		IBitboard northEastShift = Bitboard.shiftNorthEast(piece);
		while (
			canSlideEast(northEastRay, northEastShift, occupiedSquares) &&
			!Bitboard.overlap(piece, Board.eighthRank)
		) {
			northEastRay.merge(northEastShift);
			northEastShift = Bitboard.shiftNorthEast(northEastShift);
		}
		return northEastRay;
	}

	protected IBitboard getSouthWestRay(IBitboard piece, IBitboard occupiedSquares) {
		IBitboard southWestRay = new Bitboard();
		IBitboard southWestShift = Bitboard.shiftSouthWest(piece);
		while (
			canSlideWest(southWestRay, southWestShift, occupiedSquares) &&
			!Bitboard.overlap(piece, Board.firstRank)
		) {
			southWestRay.merge(southWestShift);
			southWestShift = Bitboard.shiftSouthWest(southWestShift);
		}
		return southWestRay;
	}

	protected IBitboard getSouthEastRay(IBitboard piece, IBitboard occupiedSquares) {
		IBitboard southEastRay = new Bitboard();
		IBitboard southEastShift = Bitboard.shiftSouthEast(piece);
		while (
			canSlideEast(southEastRay, southEastShift, occupiedSquares) &&
			!Bitboard.overlap(piece, Board.firstRank)
		) {
			southEastRay.merge(southEastShift);
			southEastShift = Bitboard.shiftSouthEast(southEastShift);
		}
		return southEastRay;
	}

	private boolean pathFree(IBitboard slided, IBitboard occupiedSquares) {
		return !Bitboard.overlap(slided, occupiedSquares);
	}

	private boolean boardWestEdgeUnreached(IBitboard piece) {
		return !Bitboard.overlap(piece, Board.firstFile);
	}

	private boolean boardEastEdgeUnreached(IBitboard piece) {
		return !Bitboard.overlap(piece, Board.lastFile);
	}

	private boolean boardNorthEdgeUnreached(IBitboard piece) {
		return !Bitboard.overlap(piece, Board.firstFile);
	}

	private boolean boardSouthEdgeUnreached(IBitboard piece) {
		return !Bitboard.overlap(piece, Board.lastFile);
	}

	private boolean canSlideWest(IBitboard piece, IBitboard slided, IBitboard occupiedSquares) {
		return pathFree(slided, occupiedSquares) && boardWestEdgeUnreached(piece);
	}

	private boolean canSlideEast(IBitboard piece, IBitboard slided, IBitboard occupiedSquares) {
		return pathFree(slided, occupiedSquares) && boardEastEdgeUnreached(piece);
	}

	private boolean canSlideNorth(IBitboard piece, IBitboard slided, IBitboard occupiedSquares) {
		return pathFree(slided, occupiedSquares) && boardNorthEdgeUnreached(piece);
	}

	private boolean canSlideSouth(IBitboard piece, IBitboard slided, IBitboard occupiedSquares) {
		return pathFree(slided, occupiedSquares) && boardSouthEdgeUnreached(piece);
	}

	private void setVariant(Pieces variant) {
		this.variant = variant;
	}
}

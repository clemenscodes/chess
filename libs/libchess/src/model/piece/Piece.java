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

	public Pieces getVariant() {
		return variant;
	}

	private void setVariant(Pieces variant) {
		this.variant = variant;
	}

	private IBitboard bitboard;

	public IBitboard getBitboard() {
		return bitboard;
	}

	private void setBitboard(IBitboard bitboard) {
		this.bitboard = bitboard;
	}

	public Piece(Pieces variant) {
		setVariant(variant);
		setBitboard(new Bitboard());
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

	public IBitboard getMoveMask(int source, int destination) {
		return Bitboard.merge(Bitboard.getSingleBit(source), Bitboard.getSingleBit(destination));
	}

	public IBitboard getAllAttacks(IBoard board) {
		return getAllSlidingAttacks(board);
	}

	protected IBitboard getAllSlidingAttacks(IBoard board) {
		return Bitboard
			.split(getBitboard())
			.stream()
			.map(slider -> getAttacks(slider, board))
			.reduce(Bitboard::merge)
			.orElse(new Bitboard());
	}

	protected IBitboard removeFriendlyPieces(IBitboard piece, IBoard board) {
		return Bitboard.intersect(piece, Bitboard.negate(board.getFriendlyPieces()));
	}

	protected boolean isInvalidMove(int source, int destination, IBoard board) {
		System.out.println(board.getAllOpponentAttacks());
		return !(
			Bitboard.checkBit(getBitboard(), source) &&
			Bitboard.checkBit(getAttacks(Bitboard.getSingleBit(source), board), destination)
		);
	}

	protected IBitboard getDiagonalRays(IBitboard piece, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] {
				getNorthEastRay(piece, board),
				getNorthWestRay(piece, board),
				getSouthWestRay(piece, board),
				getSouthEastRay(piece, board),
			}
		);
	}

	protected IBitboard getHorizontalRays(IBitboard piece, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] { getEastRay(piece, board), getWestRay(piece, board) }
		);
	}

	protected IBitboard getVerticalRays(IBitboard piece, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] { getNorthRay(piece, board), getSouthRay(piece, board) }
		);
	}

	private IBitboard getNorthWestRay(IBitboard piece, IBoard board) {
		IBitboard northWestRay = new Bitboard();
		IBitboard northWestShift = piece.copy();
		while (canSlideNorthWest(northWestShift, board)) {
			northWestShift = Bitboard.shiftNorthWest(northWestShift);
			northWestRay.merge(northWestShift);
		}
		northWestShift = Bitboard.shiftNorthWest(northWestShift);
		if (isEnemyCollision(northWestShift, board)) {
			northWestRay.merge(northWestShift);
		}
		return northWestRay;
	}

	private IBitboard getNorthRay(IBitboard piece, IBoard board) {
		IBitboard northRay = new Bitboard();
		IBitboard northShift = piece.copy();
		while (canSlideNorth(northShift, board)) {
			northShift = Bitboard.shiftNorth(northShift);
			northRay.merge(northShift);
		}
		northShift = Bitboard.shiftNorth(northShift);
		if (isEnemyCollision(northShift, board)) {
			northRay.merge(northShift);
		}
		return northRay;
	}

	private IBitboard getNorthEastRay(IBitboard piece, IBoard board) {
		IBitboard northEastRay = new Bitboard();
		IBitboard northEastShift = piece.copy();
		while (canSlideNorthEast(northEastShift, board)) {
			northEastShift = Bitboard.shiftNorthEast(northEastShift);
			northEastRay.merge(northEastShift);
		}
		northEastShift = Bitboard.shiftNorthEast(northEastShift);
		if (isEnemyCollision(northEastShift, board)) {
			northEastRay.merge(northEastShift);
		}
		return northEastRay;
	}

	private IBitboard getEastRay(IBitboard piece, IBoard board) {
		IBitboard eastRay = new Bitboard();
		IBitboard eastShift = piece.copy();
		while (canSlideEast(eastShift, board)) {
			eastShift = Bitboard.shiftEast(eastShift);
			eastRay.merge(eastShift);
		}
		eastShift = Bitboard.shiftEast(eastShift);
		if (isEnemyCollision(eastShift, board)) {
			eastRay.merge(eastShift);
		}
		return eastRay;
	}

	private IBitboard getSouthEastRay(IBitboard piece, IBoard board) {
		IBitboard southEastRay = new Bitboard();
		IBitboard southEastShift = piece.copy();
		while (canSlideSouthEast(southEastShift, board)) {
			southEastShift = Bitboard.shiftSouthEast(southEastShift);
			southEastRay.merge(southEastShift);
		}
		southEastShift = Bitboard.shiftSouthEast(southEastShift);
		if (isEnemyCollision(southEastShift, board)) {
			southEastRay.merge(southEastShift);
		}
		return southEastRay;
	}

	private IBitboard getSouthRay(IBitboard piece, IBoard board) {
		IBitboard southRay = new Bitboard();
		IBitboard southShift = piece.copy();
		while (canSlideSouth(southShift, board)) {
			southShift = Bitboard.shiftSouth(southShift);
			southRay.merge(southShift);
		}
		southShift = Bitboard.shiftSouth(southShift);
		if (isEnemyCollision(southShift, board)) {
			southRay.merge(southShift);
		}
		return southRay;
	}

	private IBitboard getSouthWestRay(IBitboard piece, IBoard board) {
		IBitboard southWestRay = new Bitboard();
		IBitboard southWestShift = piece.copy();
		while (canSlideSouthWest(southWestShift, board)) {
			southWestShift = Bitboard.shiftSouthWest(southWestShift);
			southWestRay.merge(southWestShift);
		}
		southWestShift = Bitboard.shiftSouthWest(southWestShift);
		if (isEnemyCollision(southWestShift, board)) {
			southWestRay.merge(southWestShift);
		}
		return southWestRay;
	}

	private IBitboard getWestRay(IBitboard piece, IBoard board) {
		IBitboard westRay = new Bitboard();
		IBitboard westShift = piece.copy();
		while (canSlideWest(westShift, board)) {
			westShift = Bitboard.shiftWest(westShift);
			westRay.merge(westShift);
		}
		westShift = Bitboard.shiftWest(westShift);
		if (isEnemyCollision(westShift, board)) {
			westRay.merge(westShift);
		}
		return westRay;
	}

	private boolean isFriendlyCollision(IBitboard piece, IBoard board) {
		return Bitboard.overlap(piece, board.getFriendlyPieces());
	}

	private boolean isEnemyCollision(IBitboard piece, IBoard board) {
		return Bitboard.overlap(piece, board.getOpponentPieces());
	}

	private boolean pathFree(IBitboard slided, IBoard board) {
		if (isFriendlyCollision(slided, board)) {
			return false;
		}
		return !isEnemyCollision(slided, board);
	}

	private boolean westEdge(IBitboard piece) {
		return Bitboard.overlap(piece, Board.firstFile);
	}

	private boolean eastEdge(IBitboard piece) {
		return Bitboard.overlap(piece, Board.lastFile);
	}

	private boolean northEdge(IBitboard piece) {
		return Bitboard.overlap(piece, Board.eighthRank);
	}

	private boolean southEdge(IBitboard piece) {
		return Bitboard.overlap(piece, Board.firstRank);
	}

	private boolean canSlideNorthWest(IBitboard piece, IBoard board) {
		if (northEdge(piece) || westEdge(piece)) {
			return false;
		}
		IBitboard slided = Bitboard.shiftNorthWest(piece);
		return pathFree(slided, board);
	}

	private boolean canSlideNorthEast(IBitboard piece, IBoard board) {
		if (northEdge(piece) || eastEdge(piece)) {
			return false;
		}
		IBitboard slided = Bitboard.shiftNorthEast(piece);
		return pathFree(slided, board);
	}

	private boolean canSlideNorth(IBitboard piece, IBoard board) {
		if (northEdge(piece)) {
			return false;
		}
		IBitboard slided = Bitboard.shiftNorth(piece);
		return pathFree(slided, board);
	}

	private boolean canSlideEast(IBitboard piece, IBoard board) {
		if (eastEdge(piece)) {
			return false;
		}
		IBitboard slided = Bitboard.shiftEast(piece);
		return pathFree(slided, board);
	}

	private boolean canSlideSouth(IBitboard piece, IBoard board) {
		if (southEdge(piece)) {
			return false;
		}
		IBitboard slided = Bitboard.shiftSouth(piece);
		return pathFree(slided, board);
	}

	private boolean canSlideWest(IBitboard piece, IBoard board) {
		if (westEdge(piece)) {
			return false;
		}
		IBitboard slided = Bitboard.shiftWest(piece);
		return pathFree(slided, board);
	}

	private boolean canSlideSouthWest(IBitboard piece, IBoard board) {
		if (southEdge(piece) || westEdge(piece)) {
			return false;
		}
		IBitboard slided = Bitboard.shiftSouthWest(piece);
		return pathFree(slided, board);
	}

	private boolean canSlideSouthEast(IBitboard piece, IBoard board) {
		if (southEdge(piece) || eastEdge(piece)) {
			return false;
		}
		IBitboard slided = Bitboard.shiftSouthEast(piece);
		return pathFree(slided, board);
	}
}

package model.piece;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.move.IMove;
import model.move.Move;
import model.move.irreversible.capturing.CaptureMove;
import model.move.reversible.QuietMove;
import model.piece.pawn.Pawn;

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

	public Piece(Pieces variant, IBitboard bitboard) {
		setVariant(variant);
		setBitboard(bitboard);
	}

	public IMove move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		return unsafeMove(source, destination, board);
	}

	public IBitboard getMoveMask(int source, int destination) {
		return Bitboard.merge(Bitboard.getSingleBit(source), Bitboard.getSingleBit(destination));
	}

	public IBitboard getAllAttacks(IBoard board) {
		return getAllSlidingAttacks(board);
	}

	public ArrayList<Square[]> getMoveDestinations(IBoard board) {
		ArrayList<Square[]> allDestinations = new ArrayList<>();
		ArrayList<IBitboard> pieces = Bitboard.split(getBitboard());
		pieces.forEach(piece -> {
			Square pieceSourceSquare = getSquareFromSingleBit(piece);
			IBitboard pieceAttacks = this instanceof Pawn pawn
				? pawn.getTargets(piece, board)
				: getAttacks(piece, board);
			ArrayList<IBitboard> splitAttacks = Bitboard.split(pieceAttacks);
			splitAttacks.forEach(bitboard -> {
				Square[] squarePair = new Square[2];
				squarePair[0] = pieceSourceSquare;
				squarePair[1] = getSquareFromSingleBit(bitboard);
				allDestinations.add(squarePair);
			});
		});
		return allDestinations;
	}

	private Square getSquareFromSingleBit(IBitboard board) {
		for (int i = 0; i < 64; i++) {
			IBitboard mask = Bitboard.leftShiftMask(i);
			if (Bitboard.overlap(board, mask)) {
				return Square.getSquare(i);
			}
		}
		return null;
	}

	protected IBitboard removeFriendlyPieces(IBitboard piece, IBoard board) {
		return Bitboard.intersect(
			piece,
			Bitboard.negate(board.getPieces(board.getFen().isWhite()))
		);
	}

	protected boolean isInvalidMove(int source, int destination, IBoard board) {
		return !(
			sourceSquareHasPiece(source) &&
			pieceCanMoveToDestination(source, destination, board) &&
			kingSafe(source, destination, board)
		);
	}

	protected boolean pieceCanMoveToDestination(int source, int destination, IBoard board) {
		return Bitboard.checkBit(getAttacks(Bitboard.getSingleBit(source), board), destination);
	}

	protected boolean sourceSquareHasPiece(int source) {
		return Bitboard.checkBit(getBitboard(), source);
	}

	protected IBoard simulateMove(int source, int destination, IBoard board) {
		IBoard copiedBoard = null;
		try {
			copiedBoard = board.deepCopy();
		} catch (IOException | ClassNotFoundException ignored) {}
		unsafeMove(source, destination, copiedBoard);
		return copiedBoard;
	}

	protected boolean kingSafe(int source, int destination, IBoard board) {
		IBoard simulatedBoard = simulateMove(source, destination, board);
		boolean kingAttacked = Bitboard.overlap(
			board.getKing(board.getFen().isWhite()),
			simulatedBoard.getAllOpponentAttacks()
		);
		if (kingAttacked) {
			throw new Error("King is in check");
		}
		return true;
	}

	protected IMove unsafeMove(int source, int destination, IBoard board) {
		Square src = Square.getSquare(source);
		Square dst = Square.getSquare(destination);
		if (Move.isCapture(Bitboard.getSingleBit(destination), board)) {
			return new CaptureMove(src, dst, board);
		}

		return new QuietMove(src, dst, board);
	}

	private IBitboard getAllSlidingAttacks(IBoard board) {
		return Bitboard
			.split(getBitboard())
			.stream()
			.map(slider -> getAttacks(slider, board))
			.reduce(Bitboard::merge)
			.orElse(new Bitboard());
	}
}

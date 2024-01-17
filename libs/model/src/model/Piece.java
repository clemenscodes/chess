package model;

import static api.model.Pieces.*;

import api.model.Pieces;
import api.model.Square;
import java.io.IOException;
import java.util.ArrayList;

abstract class Piece implements IPiece {

	static final char BLACK_BISHOP = '♝';
	static final char WHITE_BISHOP = '♗';
	static final char BLACK_KING = '♚';
	static final char WHITE_KING = '♔';
	static final char BLACK_KNIGHT = '♞';
	static final char WHITE_KNIGHT = '♘';
	static final char BLACK_PAWN = '♟';
	static final char WHITE_PAWN = '♙';
	static final char BLACK_QUEEN = '♛';
	static final char WHITE_QUEEN = '♕';
	static final char BLACK_ROOK = '♜';
	static final char WHITE_ROOK = '♖';
	static final char EMPTY_SYMBOL = ' ';

	static Pieces fromSymbol(char symbol) {
		return switch (symbol) {
			case 'r' -> BlackRook;
			case 'n' -> BlackKnight;
			case 'b' -> BlackBishop;
			case 'q' -> BlackQueen;
			case 'k' -> BlackKing;
			case 'p' -> BlackPawn;
			case 'R' -> WhiteRook;
			case 'N' -> WhiteKnight;
			case 'B' -> WhiteBishop;
			case 'Q' -> WhiteQueen;
			case 'K' -> WhiteKing;
			case 'P' -> WhitePawn;
			default -> throw new IllegalStateException("Unexpected symbol: " + symbol);
		};
	}

	static char fromKind(Pieces kind) {
		return switch (kind) {
			case BlackRook -> 'r';
			case BlackKnight -> 'n';
			case BlackBishop -> 'b';
			case BlackQueen -> 'q';
			case BlackKing -> 'k';
			case BlackPawn -> 'p';
			case WhiteRook -> 'R';
			case WhiteKnight -> 'N';
			case WhiteBishop -> 'B';
			case WhiteQueen -> 'Q';
			case WhiteKing -> 'K';
			case WhitePawn -> 'P';
		};
	}

	static final char[] SYMBOLS = {
		BLACK_ROOK,
		BLACK_KNIGHT,
		BLACK_BISHOP,
		BLACK_QUEEN,
		BLACK_KING,
		BLACK_PAWN,
		WHITE_ROOK,
		WHITE_KNIGHT,
		WHITE_BISHOP,
		WHITE_QUEEN,
		WHITE_KING,
		WHITE_PAWN,
	};

	static final Pieces[] PIECE_BY_INDEX = {
		BlackRook,
		BlackKnight,
		BlackBishop,
		BlackQueen,
		BlackKing,
		BlackPawn,
		WhiteRook,
		WhiteKnight,
		WhiteBishop,
		WhiteQueen,
		WhiteKing,
		WhitePawn,
	};

	static Pieces getSelectedPiece(Pieces[] pieces, String userInput) {
		return switch (userInput) {
			case "Q" -> pieces[0];
			case "R" -> pieces[1];
			case "N" -> pieces[2];
			case "B" -> pieces[3];
			default -> throw new Error("Invalid input");
		};
	}

	static Pieces[] getWhitePromotionPieces() {
		return new Pieces[] {
			Pieces.WhiteQueen,
			Pieces.WhiteRook,
			Pieces.WhiteKnight,
			Pieces.WhiteBishop,
		};
	}

	static Pieces[] getBlackPromotionPieces() {
		return new Pieces[] {
			Pieces.BlackQueen,
			Pieces.BlackRook,
			Pieces.BlackKnight,
			Pieces.BlackBishop,
		};
	}

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

	Piece(Pieces variant) {
		setVariant(variant);
		setBitboard(new Bitboard());
	}

	Piece(Pieces variant, IBitboard bitboard) {
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

	public ArrayList<Square[]> getMoves(IBoard board) {
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
		Square square = null;
		for (int i = 0; i < 64; i++) {
			IBitboard mask = Bitboard.leftShiftMask(i);
			if (Bitboard.overlap(board, mask)) {
				square = Board.getSquare(i);
			}
		}
		return square;
	}

	protected IBitboard removeFriendlyPieces(IBitboard piece, IBoard board) {
		return Bitboard.intersect(
			piece,
			Bitboard.negate(board.getPieces(board.getFen().isWhite()))
		);
	}

	public boolean isInvalidMove(int source, int destination, IBoard board) {
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
		Square src = Board.getSquare(source);
		Square dst = Board.getSquare(destination);
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

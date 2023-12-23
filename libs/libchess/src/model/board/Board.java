package model.board;

import static model.piece.Pieces.SYMBOLS;

import java.io.Serializable;
import model.piece.Piece;
import model.piece.Pieces;
import model.piece.bishop.extension.BlackBishop;
import model.piece.bishop.extension.WhiteBishop;
import model.piece.king.extension.BlackKing;
import model.piece.king.extension.WhiteKing;
import model.piece.knight.extension.BlackKnight;
import model.piece.knight.extension.WhiteKnight;
import model.piece.pawn.extension.BlackPawn;
import model.piece.pawn.extension.WhitePawn;
import model.piece.queen.extension.BlackQueen;
import model.piece.queen.extension.WhiteQueen;
import model.piece.rook.extension.BlackRook;
import model.piece.rook.extension.WhiteRook;

public class Board implements IBoard, Serializable {

	public static final byte SIZE = 8;
	public static final byte NORTH = SIZE;
	public static final byte EAST = 1;
	public static final byte SOUTH = -SIZE;
	public static final byte WEST = -1;
	public static final byte NORTH_EAST = NORTH + EAST;
	public static final byte SOUTH_EAST = SOUTH + EAST;
	public static final byte SOUTH_WEST = SOUTH + WEST;
	public static final byte NORTH_WEST = NORTH + WEST;
	public static final byte NORTH_NORTH_EAST = NORTH + NORTH_EAST;
	public static final byte NORTH_NORTH_WEST = NORTH + NORTH_WEST;
	public static final byte SOUTH_SOUTH_EAST = SOUTH + SOUTH_EAST;
	public static final byte SOUTH_SOUTH_WEST = SOUTH + SOUTH_WEST;
	public static final byte EAST_EAST_NORTH = EAST + NORTH_EAST;
	public static final byte EAST_EAST_SOUTH = EAST + SOUTH_EAST;
	public static final byte WEST_WEST_NORTH = WEST + NORTH_WEST;
	public static final byte WEST_WEST_SOUTH = WEST + SOUTH_WEST;
	public static final Bitboard firstFile = new Bitboard(0x0101010101010101L);
	public static final Bitboard lastFile = new Bitboard(0x8080808080808080L);
	public static final Bitboard firstRank = new Bitboard(0x00000000000000FFL);
	public static final Bitboard lastRank = new Bitboard(0xFF00000000000000L);
	public static final Bitboard firstDiagonal = new Bitboard(
		0x8040201008040201L
	);
	public static final Bitboard lastDiagonal = new Bitboard(
		0x0102040810204080L
	);
	public static final Bitboard lightSquares = new Bitboard(
		0x55AA55AA55AA55AAL
	);
	public static final Bitboard darkSquares = new Bitboard(
		0xAA55AA55AA55AA55L
	);

	private WhiteKing whiteKing;
	private WhiteQueen whiteQueen;
	private WhiteRook whiteRook;
	private WhiteKnight whiteKnight;
	private WhiteBishop whiteBishop;
	private WhitePawn whitePawn;
	private BlackKing blackKing;
	private BlackQueen blackQueen;
	private BlackRook blackRook;
	private BlackKnight blackKnight;
	private BlackBishop blackBishop;
	private BlackPawn blackPawn;

	public Board() {
		setBlackRook(new BlackRook());
		setBlackKnight(new BlackKnight());
		setBlackBishop(new BlackBishop());
		setBlackQueen(new BlackQueen());
		setBlackKing(new BlackKing());
		setBlackPawn(new BlackPawn());
		setWhiteRook(new WhiteRook());
		setWhiteKnight(new WhiteKnight());
		setWhiteBishop(new WhiteBishop());
		setWhiteQueen(new WhiteQueen());
		setWhiteKing(new WhiteKing());
		setWhitePawn(new WhitePawn());
	}

	public void initializePieces(String[] ppd) {
		int reverseRank = 0;
		for (int rank = 7; rank >= 0; rank--) {
			int file = 0;
			for (char pieceChar : ppd[reverseRank].toCharArray()) {
				if (Character.isDigit(pieceChar)) {
					file += Character.getNumericValue(pieceChar);
				} else {
					initializePiece(pieceChar, rank, file++);
				}
			}
			reverseRank++;
		}
	}

	public long[] getAllPieces() {
		return new long[] {
			getBlackRook().getBitboard().getBits(),
			getBlackKnight().getBitboard().getBits(),
			getBlackBishop().getBitboard().getBits(),
			getBlackQueen().getBitboard().getBits(),
			getBlackKing().getBitboard().getBits(),
			getBlackPawn().getBitboard().getBits(),
			getWhiteRook().getBitboard().getBits(),
			getWhiteKnight().getBitboard().getBits(),
			getWhiteBishop().getBitboard().getBits(),
			getWhiteQueen().getBitboard().getBits(),
			getWhiteKing().getBitboard().getBits(),
			getWhitePawn().getBitboard().getBits(),
		};
	}

	public Pieces getPieceByIndex(int index) {
		long[] allPieces = getAllPieces();
		long mask = 1L << index;
		for (int i = 0; i < allPieces.length; i++) {
			if ((allPieces[i] & mask) != 0) {
				return Pieces.PIECE_BY_INDEX[i];
			}
		}
		throw new Error("No piece is set on the square");
	}

	public WhiteKing getWhiteKing() {
		return whiteKing;
	}

	public WhiteQueen getWhiteQueen() {
		return whiteQueen;
	}

	public WhiteRook getWhiteRook() {
		return whiteRook;
	}

	public WhiteKnight getWhiteKnight() {
		return whiteKnight;
	}

	public WhiteBishop getWhiteBishop() {
		return whiteBishop;
	}

	public WhitePawn getWhitePawn() {
		return whitePawn;
	}

	public BlackKing getBlackKing() {
		return blackKing;
	}

	public BlackQueen getBlackQueen() {
		return blackQueen;
	}

	public BlackRook getBlackRook() {
		return blackRook;
	}

	public BlackKnight getBlackKnight() {
		return blackKnight;
	}

	public BlackBishop getBlackBishop() {
		return blackBishop;
	}

	public BlackPawn getBlackPawn() {
		return blackPawn;
	}

	private void setBlackPawn(BlackPawn blackPawn) {
		this.blackPawn = blackPawn;
	}

	private void setBlackBishop(BlackBishop blackBishop) {
		this.blackBishop = blackBishop;
	}

	private void setBlackKnight(BlackKnight blackKnight) {
		this.blackKnight = blackKnight;
	}

	private void setBlackRook(BlackRook blackRook) {
		this.blackRook = blackRook;
	}

	private void setBlackQueen(BlackQueen blackQueen) {
		this.blackQueen = blackQueen;
	}

	private void setBlackKing(BlackKing blackKing) {
		this.blackKing = blackKing;
	}

	private void setWhitePawn(WhitePawn whitePawn) {
		this.whitePawn = whitePawn;
	}

	private void setWhiteKnight(WhiteKnight whiteKnight) {
		this.whiteKnight = whiteKnight;
	}

	private void setWhiteKing(WhiteKing whiteKing) {
		this.whiteKing = whiteKing;
	}

	private void setWhiteQueen(WhiteQueen whiteQueen) {
		this.whiteQueen = whiteQueen;
	}

	private void setWhiteRook(WhiteRook whiteRook) {
		this.whiteRook = whiteRook;
	}

	private void setWhiteBishop(WhiteBishop whiteBishop) {
		this.whiteBishop = whiteBishop;
	}

	private void initializePiece(char symbol, int rank, int file) {
		Pieces kind = Pieces.fromSymbol(symbol);
		Piece piece = getPiece(kind);
		long bits = piece.getBitboard().getBits();
		int index = rank * SIZE + file;
		long newBit = 1L << (index);
		long newBits = bits | newBit;
		piece.getBitboard().setBits(newBits);
		setPiece(piece);
	}

	private Piece getPiece(Pieces kind) {
		return switch (kind) {
			case BlackRook -> getBlackRook();
			case BlackKnight -> getBlackKnight();
			case BlackBishop -> getBlackBishop();
			case BlackQueen -> getBlackQueen();
			case BlackKing -> getBlackKing();
			case BlackPawn -> getBlackPawn();
			case WhiteRook -> getWhiteRook();
			case WhiteKnight -> getWhiteKnight();
			case WhiteBishop -> getWhiteBishop();
			case WhiteQueen -> getWhiteQueen();
			case WhiteKing -> getWhiteKing();
			case WhitePawn -> getWhitePawn();
		};
	}

	private void setPiece(Piece piece) {
		switch (piece.getVariant()) {
			case BlackRook -> setBlackRook((BlackRook) piece);
			case BlackKnight -> setBlackKnight((BlackKnight) piece);
			case BlackBishop -> setBlackBishop((BlackBishop) piece);
			case BlackQueen -> setBlackQueen((BlackQueen) piece);
			case BlackKing -> setBlackKing((BlackKing) piece);
			case BlackPawn -> setBlackPawn((BlackPawn) piece);
			case WhiteRook -> setWhiteRook((WhiteRook) piece);
			case WhiteKnight -> setWhiteKnight((WhiteKnight) piece);
			case WhiteBishop -> setWhiteBishop((WhiteBishop) piece);
			case WhiteQueen -> setWhiteQueen((WhiteQueen) piece);
			case WhiteKing -> setWhiteKing((WhiteKing) piece);
			case WhitePawn -> setWhitePawn((WhitePawn) piece);
		}
	}

	private char getPieceSymbol(int squareIndex, long[] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			if ((pieces[i] & (1L << squareIndex)) != 0) {
				return SYMBOLS[i];
			}
		}
		return ' ';
	}

	@Override
	public String toString() {
		var pieces = getAllPieces();
		var stringBuilder = new StringBuilder();
		for (int rank = 7; rank >= 0; rank--) {
			for (int file = 0; file < SIZE; file++) {
				char pieceSymbol = getPieceSymbol(rank * SIZE + file, pieces);
				stringBuilder
					.append('[')
					.append(pieceSymbol)
					.append(']')
					.append(' ');
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
}

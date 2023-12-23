package model;

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

public class Board implements Serializable {

	public static final int DIMENSION = 8;
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
	private long occupiedSquares;

	public Board(ForsythEdwardsNotation fen) {
		String[] ppd = fen.getPiecePlacementData();
		initializePieces(ppd);
		calculateBoard();
	}

	public long getOccupiedSquares() {
		return occupiedSquares;
	}

	public long getEmptySquares() {
		return ~occupiedSquares;
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

	private void setOccupiedSquares(long occupiedSquares) {
		this.occupiedSquares = occupiedSquares;
	}

	private void initializePiece(char symbol, int rank, int file) {
		Pieces kind = Pieces.fromSymbol(symbol);
		Piece piece = getPieceFromKind(kind);
		long bits = piece.getBits();
		long newBit = 1L << (rank * DIMENSION + file);
		piece.setBits(bits | newBit);
		setPiece(piece);
	}

	private void initializePieces(String[] ppd) {
		for (int rank = 0; rank < ppd.length; rank++) {
			int file = 0;
			for (char pieceChar : ppd[rank].toCharArray()) {
				if (Character.isDigit(pieceChar)) {
					file += Character.getNumericValue(pieceChar);
				} else {
					initializePiece(pieceChar, rank, file++);
				}
			}
		}
	}

	private Piece getPieceFromKind(Pieces kind) {
		return switch (kind) {
			case BlackRook -> getBlackRook() != null
				? getBlackRook()
				: new BlackRook();
			case BlackKnight -> getBlackKnight() != null
				? getBlackKnight()
				: new BlackKnight();
			case BlackBishop -> getBlackBishop() != null
				? getBlackBishop()
				: new BlackBishop();
			case BlackQueen -> getBlackQueen() != null
				? getBlackQueen()
				: new BlackQueen();
			case BlackKing -> getBlackKing() != null
				? getBlackKing()
				: new BlackKing();
			case BlackPawn -> getBlackPawn() != null
				? getBlackPawn()
				: new BlackPawn();
			case WhiteRook -> getWhiteRook() != null
				? getWhiteRook()
				: new WhiteRook();
			case WhiteKnight -> getWhiteKnight() != null
				? getWhiteKnight()
				: new WhiteKnight();
			case WhiteBishop -> getWhiteBishop() != null
				? getWhiteBishop()
				: new WhiteBishop();
			case WhiteQueen -> getWhiteQueen() != null
				? getWhiteQueen()
				: new WhiteQueen();
			case WhiteKing -> getWhiteKing() != null
				? getWhiteKing()
				: new WhiteKing();
			case WhitePawn -> getWhitePawn() != null
				? getWhitePawn()
				: new WhitePawn();
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

	private void calculateBoard() {
		var occupiedSquares = 0L;
		var pieces = getAllPieces();
		for (long piece : pieces) {
			occupiedSquares |= piece;
		}
		setOccupiedSquares(occupiedSquares);
	}

	private long[] getAllPieces() {
		return new long[] {
			getBlackRook().getBits(),
			getBlackKnight().getBits(),
			getBlackBishop().getBits(),
			getBlackQueen().getBits(),
			getBlackKing().getBits(),
			getBlackPawn().getBits(),
			getWhiteRook().getBits(),
			getWhiteKnight().getBits(),
			getWhiteBishop().getBits(),
			getWhiteQueen().getBits(),
			getWhiteKing().getBits(),
			getWhitePawn().getBits(),
		};
	}

	private char getPieceSymbol(int squareIndex, long[] pieces) {
		char[] symbols = {
			BlackRook.SYMBOL,
			BlackKnight.SYMBOL,
			BlackBishop.SYMBOL,
			BlackQueen.SYMBOL,
			BlackKing.SYMBOL,
			BlackPawn.SYMBOL,
			WhiteRook.SYMBOL,
			WhiteKnight.SYMBOL,
			WhiteBishop.SYMBOL,
			WhiteQueen.SYMBOL,
			WhiteKing.SYMBOL,
			WhitePawn.SYMBOL,
		};

		for (int i = 0; i < pieces.length; i++) {
			if ((pieces[i] & (1L << squareIndex)) != 0) {
				return symbols[i];
			}
		}

		return ' ';
	}

	@Override
	public String toString() {
		var pieces = getAllPieces();
		var stringBuilder = new StringBuilder();
		for (int rank = 0; rank < DIMENSION; rank++) {
			for (int file = 0; file < DIMENSION; file++) {
				char pieceSymbol = getPieceSymbol(
					rank * DIMENSION + file,
					pieces
				);
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

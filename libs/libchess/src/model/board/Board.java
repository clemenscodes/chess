package model.board;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
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

	public static int getSquareIndex(int rank, int file) {
		return SIZE * rank + file;
	}

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
	private IBitboard whitePieces;
	private IBitboard blackPieces;
	private IBitboard occupiedSquares;
	private IBitboard emptySquares;

	public Board() {
		initializeBoard();
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

	public IBitboard getWhitePieces() {
		updateWhitePieces();
		return whitePieces;
	}

	public IBitboard getBlackPieces() {
		updateBlackPieces();
		return blackPieces;
	}

	public IBitboard getOccupiedSquares() {
		updateOccupiedSquares();
		return occupiedSquares;
	}

	public IBitboard getEmptySquares() {
		updateEmptySquares();
		return emptySquares;
	}

	public IBitboard[] getAllPieces() {
		updateAllPieces();
		IBitboard[] blackPieces = getAllBlackPieces();
		IBitboard[] whitePieces = getAllWhitePieces();
		IBitboard[] allPieces = new IBitboard[blackPieces.length + whitePieces.length];
		System.arraycopy(blackPieces, 0, allPieces, 0, blackPieces.length);
		System.arraycopy(whitePieces, 0, allPieces, blackPieces.length, whitePieces.length);
		return allPieces;
	}

	public Pieces getPieceByIndex(int index) {
		IBitboard[] allPieces = getAllPieces();
		for (int i = 0; i < allPieces.length; i++) {
			if (allPieces[i].overlap(Bitboard.leftShiftMask(index))) {
				return Pieces.PIECE_BY_INDEX[i];
			}
		}
		throw new Error("No piece is set on the square");
	}

	public void setPieces(String[] ppd) {
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
		updateOccupiedSquares();
		updateEmptySquares();
	}

	private void initializeBoard() {
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

	private void updateOccupiedSquares() {
		setOccupiedSquares(Bitboard.mergeMany(getAllPieces()));
	}

	private void updateEmptySquares() {
		setEmptySquares(Bitboard.negate(getOccupiedSquares()));
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

	private void setWhitePieces(IBitboard whitePieces) {
		this.whitePieces = whitePieces;
	}

	private void setBlackPieces(IBitboard blackPieces) {
		this.blackPieces = blackPieces;
	}

	private void setOccupiedSquares(IBitboard occupiedSquares) {
		this.occupiedSquares = occupiedSquares;
	}

	private void setEmptySquares(IBitboard emptySquares) {
		this.emptySquares = emptySquares;
	}

	private void updateWhitePieces() {
		IBitboard[] pieces = getAllWhitePieces();
		setWhitePieces(Bitboard.mergeMany(pieces));
	}

	private void updateAllPieces() {
		updateWhitePieces();
		updateBlackPieces();
	}

	private IBitboard[] getAllWhitePieces() {
		return new IBitboard[] {
			getWhiteRook().getBitboard(),
			getWhiteKnight().getBitboard(),
			getWhiteBishop().getBitboard(),
			getWhiteQueen().getBitboard(),
			getWhiteKing().getBitboard(),
			getWhitePawn().getBitboard(),
		};
	}

	private void updateBlackPieces() {
		IBitboard[] pieces = getAllBlackPieces();
		setBlackPieces(Bitboard.mergeMany(pieces));
	}

	private IBitboard[] getAllBlackPieces() {
		return new IBitboard[] {
			getBlackRook().getBitboard(),
			getBlackKnight().getBitboard(),
			getBlackBishop().getBitboard(),
			getBlackQueen().getBitboard(),
			getBlackKing().getBitboard(),
			getBlackPawn().getBitboard(),
		};
	}

	private void initializePiece(char symbol, int rank, int file) {
		Piece piece = getPiece(Pieces.fromSymbol(symbol));
		piece.getBitboard().merge(Bitboard.leftShiftMask(getSquareIndex(rank, file)));
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

	private char getPieceSymbol(int index, IBitboard[] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			if (pieces[i].overlap(Bitboard.leftShiftMask(index))) {
				return Pieces.SYMBOLS[i];
			}
		}
		return Pieces.EMPTY_SYMBOL;
	}

	@Override
	public String toString() {
		var pieces = getAllPieces();
		var stringBuilder = new StringBuilder();
		for (int rank = 7; rank >= 0; rank--) {
			for (int file = 0; file < SIZE; file++) {
				char pieceSymbol = getPieceSymbol(getSquareIndex(rank, file), pieces);
				stringBuilder.append('[').append(pieceSymbol).append(']').append(' ');
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
}

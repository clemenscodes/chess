package model.board;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.fen.ForsythEdwardsNotation;
import model.fen.IForsythEdwardsNotation;
import model.piece.IPiece;
import model.piece.Pieces;
import model.piece.bishop.BlackBishop;
import model.piece.bishop.WhiteBishop;
import model.piece.king.BlackKing;
import model.piece.king.WhiteKing;
import model.piece.knight.BlackKnight;
import model.piece.knight.WhiteKnight;
import model.piece.pawn.BlackPawn;
import model.piece.pawn.WhitePawn;
import model.piece.queen.BlackQueen;
import model.piece.queen.WhiteQueen;
import model.piece.rook.BlackRook;
import model.piece.rook.WhiteRook;
import model.writer.Writer;

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
	public static final IBitboard firstFile = new Bitboard(0x0101010101010101L);
	public static final IBitboard secondFile = new Bitboard(0x0202020202020202L);
	public static final IBitboard notFirstFile = Bitboard.negate(firstFile);
	public static final IBitboard lastFile = new Bitboard(0x8080808080808080L);
	public static final IBitboard notLastFile = Bitboard.negate(lastFile);
	public static final IBitboard secondLastFile = new Bitboard(0x4040404040404040L);
	public static final IBitboard firstRank = new Bitboard(0x00000000000000FFL);
	public static final IBitboard notFirstRank = Bitboard.negate(firstRank);
	public static final IBitboard secondRank = new Bitboard(0x000000000000FF00L);
	public static final IBitboard thirdRank = new Bitboard(0x0000000000FF0000L);
	public static final IBitboard fourthRank = new Bitboard(0x0000000FF000000L);
	public static final IBitboard fifthRank = new Bitboard(0x000000FF00000000L);
	public static final IBitboard sixthRank = new Bitboard(0x0000FF0000000000L);
	public static final IBitboard seventhRank = new Bitboard(0x00FF000000000000L);
	public static final IBitboard eighthRank = new Bitboard(0xFF00000000000000L);
	public static final IBitboard notEighthRank = Bitboard.negate(eighthRank);
	public static final IBitboard diagonal = new Bitboard(0x8040201008040201L);
	public static final IBitboard antiDiagonal = new Bitboard(0x0102040810204080L);
	public static final IBitboard lightSquares = new Bitboard(0x55AA55AA55AA55AAL);
	public static final IBitboard darkSquares = Bitboard.negate(lightSquares);

	public static int getSquareIndex(int rank, int file) {
		return SIZE * rank + file;
	}

	private IForsythEdwardsNotation fen;
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
		setFen(new ForsythEdwardsNotation());
		setPieces();
	}

	public Board(ForsythEdwardsNotation fen) {
		initializeBoard();
		setFen(fen);
		setPieces();
	}

	public IForsythEdwardsNotation getFen() {
		return fen;
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

	public IBitboard getOpponentPieces() {
		char color = getFen().getActiveColor();
		return switch (color) {
			case 'w' -> getBlackPieces();
			case 'b' -> getWhitePieces();
			default -> throw new IllegalStateException("Unexpected value: " + color);
		};
	}

	public Pieces getPieceByIndex(int index) {
		IBitboard[] allPieces = getAllPieces();
		for (int i = 0; i < allPieces.length; i++) {
			if (allPieces[i].overlap(Bitboard.getSingleBit(index))) {
				return Pieces.PIECE_BY_INDEX[i];
			}
		}
		throw new Error("No piece is set on the square " + Square.getSquare(index));
	}

	public IPiece getPiece(Pieces kind) {
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

	public void setPieces() {
		int reverseRank = 0;
		for (int rank = 7; rank >= 0; rank--) {
			int file = 0;
			for (char pieceChar : getFen().getPiecePlacementData()[reverseRank].toCharArray()) {
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

	public void capturePiece(int index) {
		Pieces capturedPiece = getPieceByIndex(index);
		getPiece(capturedPiece).getBitboard().toggleBits(Bitboard.getSingleBit(index));
	}

	private void setFen(IForsythEdwardsNotation fen) {
		this.fen = fen;
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
		IPiece piece = getPiece(Pieces.fromSymbol(symbol));
		piece.getBitboard().merge(Bitboard.getSingleBit(getSquareIndex(rank, file)));
		setPiece(piece);
	}

	private void setPiece(IPiece piece) {
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
			if (pieces[i].overlap(Bitboard.getSingleBit(index))) {
				return Pieces.SYMBOLS[i];
			}
		}
		return Pieces.EMPTY_SYMBOL;
	}

	@Override
	public String toString() {
		return Writer.loopOver(this::appendRank, getAllPieces()).toString();
	}

	private void appendRank(int rank, int file, StringBuilder stringBuilder, IBitboard[] pieces) {
		char pieceSymbol = getPieceSymbol(getSquareIndex(rank, file), pieces);
		stringBuilder.append('[').append(pieceSymbol).append(']').append(' ');
	}
}

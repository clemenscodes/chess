package fen;

import api.model.bits.IBitboard;
import api.model.board.IBoard;
import api.model.board.Square;
import api.model.fen.IForsythEdwardsNotation;
import bits.Bitboard;
import board.Board;
import java.io.Serializable;

public class ForsythEdwardsNotation implements IForsythEdwardsNotation, Serializable {

	private static final int MAX_HALF_MOVE_CLOCK = 150;
	private static final int MIN_HALF_MOVE_CLOCK = 0;
	private static final int MIN_FULL_MOVE_CLOCK = 1;
	private String[] piecePlacementData;
	private char activeColor;
	private String castling;
	private String enPassant;
	private int halfMoveClock;
	private int fullMoveNumber;
	private IBitboard enPassantMask;
	private boolean whiteKingCastle;
	private boolean whiteQueenCastle;
	private boolean blackKingCastle;
	private boolean blackQueenCastle;

	public ForsythEdwardsNotation() {
		parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
	}

	public ForsythEdwardsNotation(String fen) {
		parse(fen);
	}

	public void parse(String fen) {
		String[] parts = fen.split(" ");
		if (parts.length != 6) {
			throw new IllegalArgumentException(
				"Invalid FEN: It should consist of 6 space-separated parts"
			);
		}
		unsetEnPassantMask();
		setPiecePlacementData(parts[0]);
		setActiveColor(parts[1]);
		setCastling(parts[2]);
		setEnPassant(parts[3]);
		setHalfMoveClock(parts[4]);
		setFullMoveNumber(parts[5]);
	}

	public String[] getPiecePlacementData() {
		return piecePlacementData;
	}

	public char getActiveColor() {
		return activeColor;
	}

	public boolean isWhite() {
		return getActiveColor() == 'w';
	}

	public String getCastling() {
		return castling;
	}

	public String getEnPassant() {
		return enPassant;
	}

	public IBitboard getEnPassantMask() {
		return enPassantMask;
	}

	public int getHalfMoveClock() {
		return halfMoveClock;
	}

	public int getFullMoveNumber() {
		return fullMoveNumber;
	}

	public void updatePiecePlacementData(IBoard board) {
		setPiecePlacementData(board.getPiecePlacementData());
	}

	public void incrementFullMoveNumber() {
		setFullMoveNumber(String.valueOf(getFullMoveNumber() + 1));
	}

	public void incrementHalfMoveClock() {
		setHalfMoveClock(String.valueOf(getHalfMoveClock() + 1));
	}

	public void resetHalfMoveClock() {
		setHalfMoveClock("0");
	}

	public void switchActiveColor() {
		switch (getActiveColor()) {
			case 'w' -> setActiveColor("b");
			case 'b' -> setActiveColor("w");
		}
	}

	public void setEnPassantTargetSquare(Square square) {
		setEnPassant(square.name());
		setEnPassantMask(square);
	}

	public boolean getWhiteKingCastle() {
		return whiteKingCastle;
	}

	public boolean getWhiteQueenCastle() {
		return whiteQueenCastle;
	}

	public boolean getBlackKingCastle() {
		return blackKingCastle;
	}

	public boolean getBlackQueenCastle() {
		return blackQueenCastle;
	}

	public void castle() {
		switch (getActiveColor()) {
			case 'w' -> whiteCastle();
			case 'b' -> blackCastle();
		}
	}

	public void kingMove() {
		switch (getActiveColor()) {
			case 'w' -> whiteKingMove();
			case 'b' -> blackKingMove();
		}
	}

	public void kingRookMove() {
		switch (getActiveColor()) {
			case 'w' -> whiteKingRookMove();
			case 'b' -> blackKingRookMove();
		}
	}

	public void queenRookMove() {
		switch (getActiveColor()) {
			case 'w' -> whiteQueenRookMove();
			case 'b' -> blackQueenRookMove();
		}
	}

	public void unsetEnPassantTargetSquare() {
		setEnPassant("-");
		unsetEnPassantMask();
	}

	private void whiteCastle() {
		setWhiteKingCastle(false);
		setWhiteQueenCastle(false);
		updateCastlingInfo();
	}

	public void whiteKingMove() {
		setWhiteKingCastle(false);
		setWhiteQueenCastle(false);
		updateCastlingInfo();
	}

	public void whiteKingRookMove() {
		setWhiteKingCastle(false);
		updateCastlingInfo();
	}

	public void whiteQueenRookMove() {
		setWhiteQueenCastle(false);
		updateCastlingInfo();
	}

	private void blackCastle() {
		setBlackKingCastle(false);
		setBlackQueenCastle(false);
		updateCastlingInfo();
	}

	private void blackKingMove() {
		setBlackKingCastle(false);
		setBlackQueenCastle(false);
		updateCastlingInfo();
	}

	private void blackKingRookMove() {
		setBlackKingCastle(false);
		updateCastlingInfo();
	}

	private void blackQueenRookMove() {
		setBlackQueenCastle(false);
		updateCastlingInfo();
	}

	private void setPiecePlacementData(String piecePlacement) {
		var ppd = piecePlacement.split("/");
		if (ppd.length != Board.SIZE) {
			throw new IllegalArgumentException("Invalid piece placement data");
		}
		for (var rank : ppd) {
			if (rank.length() > Board.SIZE) {
				throw new IllegalArgumentException(
					"Invalid piece placement data: Each rank should have at most " +
					Board.SIZE +
					" files"
				);
			}
		}
		piecePlacementData = ppd;
	}

	private void setActiveColor(String color) {
		try {
			var ac = color.charAt(0);
			if (ac != 'w' && ac != 'b') {
				throw new IllegalArgumentException("Active color must be either 'w' or 'b'");
			}
			activeColor = ac;
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid active color");
		}
	}

	private void setEnPassant(String enPassantInfo) {
		if (!enPassantInfo.equals("-")) {
			if (enPassantInfo.length() != 2 || !isValidEnPassantSquare(enPassantInfo)) {
				throw new IllegalArgumentException("Invalid en passant target square");
			}
			setEnPassantMask(Square.valueOf(enPassantInfo));
		}
		enPassant = enPassantInfo;
	}

	private void setEnPassantMask(Square square) {
		enPassantMask = Bitboard.getSingleBit(Board.getIndex(square));
	}

	private void unsetEnPassantMask() {
		enPassantMask = new Bitboard();
	}

	private boolean isValidEnPassantSquare(String square) {
		char file = square.charAt(0);
		char rank = square.charAt(1);
		return ((file >= 'a' && file <= 'h') && ((rank == '3') || (rank == '6')));
	}

	private void setHalfMoveClock(String halfMoveClockStr) {
		try {
			int halfMoveClockValue = Integer.parseInt(halfMoveClockStr);
			if (halfMoveClockValue < MIN_HALF_MOVE_CLOCK) {
				throw new IllegalArgumentException(
					"Invalid half-move clock: It cannot be negative"
				);
			}
			if (halfMoveClockValue > MAX_HALF_MOVE_CLOCK) {
				throw new IllegalArgumentException(
					"Invalid half-move clock: Maximum allowed value is 150 under the seventy-five move rule"
				);
			}
			halfMoveClock = halfMoveClockValue;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid half-move clock");
		}
	}

	private void setFullMoveNumber(String fullMoveNumberStr) {
		try {
			int fullMoveNumberValue = Integer.parseInt(fullMoveNumberStr);
			if (fullMoveNumberValue < MIN_FULL_MOVE_CLOCK) {
				throw new IllegalArgumentException(
					"Invalid full-move number: It must be greater than or equal to 1"
				);
			}
			fullMoveNumber = fullMoveNumberValue;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid full-move number");
		}
	}

	private void setCastling(String castlingInfo) {
		if (!castlingInfo.matches("^[-KQkq]*$")) {
			throw new IllegalArgumentException(
				"Invalid castling information: Use 'K', 'Q', 'k', 'q', or '-'"
			);
		}
		castling = castlingInfo;
		parseCastlingInfo();
	}

	private void updateCastlingInfo() {
		String whiteKingCastle = getWhiteKingCastle() ? "K" : "";
		String whiteQueenCastle = getWhiteQueenCastle() ? "Q" : "";
		String blackKingCastle = getBlackKingCastle() ? "k" : "";
		String blackQueenCastle = getBlackQueenCastle() ? "q" : "";
		String castlingInfo =
			whiteKingCastle + whiteQueenCastle + blackKingCastle + blackQueenCastle;
		if (castlingInfo.isEmpty()) {
			castlingInfo = "-";
		}
		setCastling(castlingInfo);
	}

	private void parseCastlingInfo() {
		String castling = getCastling();
		setWhiteKingCastle(castling.contains("K"));
		setWhiteQueenCastle(castling.contains("Q"));
		setBlackKingCastle(castling.contains("k"));
		setBlackQueenCastle(castling.contains("q"));
	}

	private void setWhiteKingCastle(boolean whiteKingCastle) {
		this.whiteKingCastle = whiteKingCastle;
	}

	private void setWhiteQueenCastle(boolean whiteQueenCastle) {
		this.whiteQueenCastle = whiteQueenCastle;
	}

	private void setBlackKingCastle(boolean blackKingCastle) {
		this.blackKingCastle = blackKingCastle;
	}

	private void setBlackQueenCastle(boolean blackQueenCastle) {
		this.blackQueenCastle = blackQueenCastle;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		String[] ppd = getPiecePlacementData();
		for (int i = 0; i < ppd.length; i++) {
			stringBuilder.append(ppd[i]);
			if (i < ppd.length - 1) {
				stringBuilder.append("/");
			}
		}
		stringBuilder.append(" ").append(getActiveColor());
		stringBuilder.append(" ").append(getCastling());
		stringBuilder.append(" ").append(getEnPassant());
		stringBuilder.append(" ").append(getHalfMoveClock());
		stringBuilder.append(" ").append(getFullMoveNumber());
		return stringBuilder.toString();
	}
}

package model.fen;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.Square;

public class ForsythEdwardsNotation implements IForsythEdwardsNotation, Serializable {

	public static final int MAX_HALF_MOVE_CLOCK = 150;
	public static final int MIN_HALF_MOVE_CLOCK = 0;
	public static final int MIN_FULL_MOVE_CLOCK = 1;
	private String[] piecePlacementData;
	private char activeColor;
	private String castling;
	private String enPassant;
	private IBitboard enPassantMask;
	private int halfMoveClock;
	private int fullMoveNumber;

	public ForsythEdwardsNotation() {
		parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
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

	public void unsetEnPassantTargetSquare() {
		setEnPassant("-");
		unsetEnPassantMask();
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

	private void setCastling(String castlingInfo) {
		try {
			if (!castlingInfo.equals("-")) {
				for (char c : castlingInfo.toCharArray()) {
					if (c != 'K' && c != 'Q' && c != 'k' && c != 'q') {
						throw new IllegalArgumentException(
							"Invalid castling information: Use 'K', 'Q', 'k', 'q', or '-'"
						);
					}
				}
			}
			castling = castlingInfo;
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid castling information");
		}
	}

	private void setEnPassant(String enPassantInfo) {
		try {
			if (!enPassantInfo.equals("-")) {
				if (enPassantInfo.length() != 2 || !isValidEnPassantSquare(enPassantInfo)) {
					throw new IllegalArgumentException("Invalid en passant target square");
				}
			}
			enPassant = enPassantInfo;
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid en passant information");
		}
	}

	private void setEnPassantMask(Square square) {
		enPassantMask = Bitboard.getSingleBit(Square.getIndex(square));
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

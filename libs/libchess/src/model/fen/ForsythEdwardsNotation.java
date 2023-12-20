package model.fen;

import model.ChessModel;

public class ForsythEdwardsNotation {

	private String[] piecePlacementData;
	private char activeColor;
	private String castling;
	private String enPassant;
	private int halfMoveClock;
	private int fullMoveNumber;

	public ForsythEdwardsNotation() {
		parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
	}

	public ForsythEdwardsNotation(String fen) {
		parse(fen);
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

	public int getHalfMoveClock() {
		return halfMoveClock;
	}

	public int getFullMoveNumber() {
		return fullMoveNumber;
	}

	private void parse(String fen) {
		String[] parts = fen.split(" ");
		if (parts.length != 6) {
			throw new IllegalArgumentException(
				"Invalid FEN: It should consist of 6 space-separated parts"
			);
		}
		setPiecePlacementData(parts[0]);
		setActiveColor(parts[1]);
		setCastling(parts[2]);
		setEnPassant(parts[3]);
		setHalfMoveClock(parts[4]);
		setFullMoveNumber(parts[5]);
	}

	private void setPiecePlacementData(String piecePlacement) {
		var ppd = piecePlacement.split("/");
		if (ppd.length != ChessModel.RANKS) {
			throw new IllegalArgumentException("Invalid piece placement data");
		}
		for (var rank : ppd) {
			if (rank.length() > ChessModel.FILES) {
				throw new IllegalArgumentException(
					"Invalid piece placement data: Each rank should have at most " +
					ChessModel.FILES +
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
				throw new IllegalArgumentException(
					"Active color must be either 'w' or 'b'"
				);
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
				if (
					enPassantInfo.length() != 2 ||
					!isValidEnPassantSquare(enPassantInfo)
				) {
					throw new IllegalArgumentException(
						"Invalid en passant target square"
					);
				}
			}
			enPassant = enPassantInfo;
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException(
				"Invalid en passant information"
			);
		}
	}

	private boolean isValidEnPassantSquare(String square) {
		char file = square.charAt(0);
		char rank = square.charAt(1);
		return (file >= 'a' && file <= 'h') && ((rank == '3') || (rank == '6'));
	}

	private void setHalfMoveClock(String halfMoveClockStr) {
		try {
			int halfMoveClockValue = Integer.parseInt(halfMoveClockStr);
			if (halfMoveClockValue < 0) {
				throw new IllegalArgumentException(
					"Invalid half-move clock: It cannot be negative"
				);
			}
			if (halfMoveClockValue > 150) {
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
			if (fullMoveNumberValue < 1) {
				throw new IllegalArgumentException(
					"Invalid full-move number: It must be greater than or equal to 1"
				);
			}
			fullMoveNumber = fullMoveNumberValue;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid full-move number");
		}
	}
}

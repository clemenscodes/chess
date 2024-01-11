package model.move;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.fen.IForsythEdwardsNotation;
import model.move.irreversible.pawn.DoublePawnPushMove;
import model.piece.rook.Rook;

public abstract class Move implements IMove, Serializable {

	public static boolean isPromotion(IBitboard destination, IBitboard promotionMask) {
		return Bitboard.overlap(destination, promotionMask);
	}

	public static boolean isCapture(IBitboard destination, IBoard board) {
		return Bitboard.overlap(
			destination,
			board.getPieces(board.getFen().getActiveColor() == 'w')
		);
	}

	public static boolean isEnPassant(IBitboard destination, IBoard board) {
		return Bitboard.overlap(destination, board.getFen().getEnPassantMask());
	}

	public static boolean isKingCastle(Square source, Square destination, IBoard board) {
		char color = board.getFen().getActiveColor();
		return switch (color) {
			case 'w' -> isWhiteKingCastle(source, destination);
			case 'b' -> isBlackKingCastle(source, destination);
			default -> throw new IllegalStateException("Unexpected value: " + color);
		};
	}

	public static boolean isQueenCastle(Square source, Square destination, IBoard board) {
		char color = board.getFen().getActiveColor();
		return switch (color) {
			case 'w' -> isWhiteQueenCastle(source, destination);
			case 'b' -> isBlackQueenCastle(source, destination);
			default -> throw new IllegalStateException("Unexpected value: " + color);
		};
	}

	public static boolean canKingCastle(Square source, Square destination, IBoard board) {
		char color = board.getFen().getActiveColor();
		return switch (color) {
			case 'w' -> whiteCanKingCastle(source, destination, board);
			case 'b' -> blackCanKingCastle(source, destination, board);
			default -> throw new IllegalStateException("Unexpected value: " + color);
		};
	}

	public static boolean canQueenCastle(Square source, Square destination, IBoard board) {
		char color = board.getFen().getActiveColor();
		return switch (color) {
			case 'w' -> whiteCanQueenCastle(source, destination, board);
			case 'b' -> blackCanQueenCastle(source, destination, board);
			default -> throw new IllegalStateException("Unexpected value: " + color);
		};
	}

	private static boolean whiteCanKingCastle(Square source, Square destination, IBoard board) {
		return isWhiteKingCastle(source, destination) && validateWhiteKingCastleRights(board);
	}

	private static boolean blackCanKingCastle(Square source, Square destination, IBoard board) {
		return isBlackKingCastle(source, destination) && validateBlackKingCastleRights(board);
	}

	private static boolean whiteCanQueenCastle(Square source, Square destination, IBoard board) {
		return isWhiteQueenCastle(source, destination) && validateWhiteQueenCastleRights(board);
	}

	private static boolean blackCanQueenCastle(Square source, Square destination, IBoard board) {
		return isBlackQueenCastle(source, destination) && validateBlackQueenCastleRights(board);
	}

	private static boolean validateWhiteKingCastleRights(IBoard board) {
		return board.getFen().getWhiteKingCastle();
	}

	private static boolean validateBlackKingCastleRights(IBoard board) {
		return board.getFen().getBlackKingCastle();
	}

	private static boolean validateWhiteQueenCastleRights(IBoard board) {
		return board.getFen().getWhiteQueenCastle();
	}

	private static boolean validateBlackQueenCastleRights(IBoard board) {
		return board.getFen().getBlackQueenCastle();
	}

	private static boolean isWhiteKingCastle(Square source, Square destination) {
		return source == Board.whiteKingSquare && destination == Board.whiteKingRookSquare;
	}

	private static boolean isBlackKingCastle(Square source, Square destination) {
		return source == Board.blackKingSquare && destination == Board.blackKingRookSquare;
	}

	private static boolean isWhiteQueenCastle(Square source, Square destination) {
		return source == Board.whiteKingSquare && destination == Board.whiteQueenRookSquare;
	}

	private static boolean isBlackQueenCastle(Square source, Square destination) {
		return source == Board.blackKingSquare && destination == Board.blackQueenRookSquare;
	}

	private Square source;
	private Square destination;

	public Move(Square source, Square destination, IBoard board) {
		if (!isPlayersPiece(board, source)) {
			throw new Error("Can not move opponents piece");
		}
		IForsythEdwardsNotation fen = board.getFen();
		if (!(this instanceof DoublePawnPushMove) && !fen.getEnPassant().equals("-")) {
			fen.unsetEnPassantTargetSquare();
		}
		if (board.getPiece(source) instanceof Rook && !fen.getCastling().equals("-")) {
			switch (source) {
				case a1, h1 -> fen.kingRookMove();
				case a8, h8 -> fen.queenRookMove();
			}
		}
		fen.incrementFullMoveNumber();
		setSource(source);
		setDestination(destination);
	}

	public Square getSource() {
		return source;
	}

	public Square getDestination() {
		return destination;
	}

	private void setSource(Square source) {
		this.source = source;
	}

	private void setDestination(Square destination) {
		this.destination = destination;
	}

	private boolean isPlayersPiece(IBoard board, Square source) {
		IBitboard player = board.getFen().getActiveColor() == 'w'
			? board.getWhitePieces()
			: board.getBlackPieces();
		IBitboard movingPiece = Bitboard.getSingleBit(Square.getIndex(source));
		return Bitboard.overlap(player, movingPiece);
	}
}

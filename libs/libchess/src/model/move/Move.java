package model.move;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.board.Square;
import model.fen.IForsythEdwardsNotation;
import model.move.irreversible.pawn.DoublePawnPushMove;
import model.piece.pawn.Pawn;
import model.piece.rook.Rook;

public abstract class Move implements IMove, Serializable {

	public static boolean isPromotion(IBitboard destination) {
		return Bitboard.overlap(destination, Pawn.promotionMask);
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
		return board.getFen().isWhite()
			? isWhiteKingCastle(source, destination)
			: isBlackKingCastle(source, destination);
	}

	public static boolean isQueenCastle(Square source, Square destination, IBoard board) {
		return board.getFen().isWhite()
			? isWhiteQueenCastle(source, destination)
			: isBlackQueenCastle(source, destination);
	}

	public static boolean canKingCastle(Square source, Square destination, IBoard board) {
		return board.getFen().isWhite()
			? whiteCanKingCastle(source, destination, board)
			: blackCanKingCastle(source, destination, board);
	}

	public static boolean canQueenCastle(Square source, Square destination, IBoard board) {
		return board.getFen().isWhite()
			? whiteCanQueenCastle(source, destination, board)
			: blackCanQueenCastle(source, destination, board);
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

	public Square getSource() {
		return source;
	}

	private void setSource(Square source) {
		this.source = source;
	}

	private Square destination;

	public Square getDestination() {
		return destination;
	}

	private void setDestination(Square destination) {
		this.destination = destination;
	}

	public Move(Square source, Square destination, IBoard board) {
		checkValidMove(board, source);
		handleEnPassant(board);
		handleCastling(board, source);
		updateForsythEdwardsNotation(board);
		setSource(source);
		setDestination(destination);
	}

	private void checkValidMove(IBoard board, Square source) {
		if (!isPlayersPiece(board, source)) {
			throw new Error("Cannot move opponent's piece");
		}
	}

	private void handleEnPassant(IBoard board) {
		IForsythEdwardsNotation fen = board.getFen();
		if (!(this instanceof DoublePawnPushMove) && enPassantPossible(fen)) {
			fen.unsetEnPassantTargetSquare();
		}
	}

	private boolean enPassantPossible(IForsythEdwardsNotation fen) {
		return !fen.getEnPassant().equals("-");
	}

	private boolean castlingPossible(IForsythEdwardsNotation fen) {
		return !fen.getCastling().equals("-");
	}

	private void handleCastling(IBoard board, Square source) {
		IForsythEdwardsNotation fen = board.getFen();
		if (board.getPiece(source) instanceof Rook && castlingPossible(fen)) {
			switch (source) {
				case a1, h1 -> fen.kingRookMove();
				case a8, h8 -> fen.queenRookMove();
			}
		}
	}

	private void updateForsythEdwardsNotation(IBoard board) {
		board.getFen().incrementFullMoveNumber();
	}

	private boolean isPlayersPiece(IBoard board, Square source) {
		IBitboard player = board.getFen().isWhite()
			? board.getWhitePieces()
			: board.getBlackPieces();
		IBitboard movingPiece = Bitboard.getSingleBit(Square.getIndex(source));
		return Bitboard.overlap(player, movingPiece);
	}
}

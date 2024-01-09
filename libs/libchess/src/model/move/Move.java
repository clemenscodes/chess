package model.move;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.pawn.DoublePawnPushMove;

public abstract class Move implements IMove, Serializable {

	public static boolean isPromotion(IBitboard destination, IBitboard promotionMask) {
		return Bitboard.overlap(destination, promotionMask);
	}

	public static boolean isCapture(IBitboard destination, IBoard board) {
		return Bitboard.overlap(destination, board.getOpponentPieces());
	}

	public static boolean isEnPassant(IBitboard destination, IBoard board) {
		return Bitboard.overlap(destination, board.getFen().getEnPassantMask());
	}

	public static boolean isKingCastle(IBitboard destination, IBoard board) {
		return false;
	}

	public static boolean isQueenCastle(IBitboard destination, IBoard board) {
		return false;
	}

	private Square source;
	private Square destination;

	public Move(Square source, Square destination, IBoard board) {
		if (!isPlayersPiece(board, source)) {
			throw new Error("Can not move opponents piece");
		}
		var fen = board.getFen();
		if (!(this instanceof DoublePawnPushMove) && !fen.getEnPassant().equals("-")) {
			fen.unsetEnPassantTargetSquare();
		}
		fen.incrementFullMoveNumber();
		fen.switchActiveColor();
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

import api.model.bits.IBitboard;
import api.model.board.IBoard;
import api.model.board.Square;
import api.model.fen.IForsythEdwardsNotation;
import api.model.move.IMove;
import java.io.Serializable;

abstract class Move implements IMove, Serializable {

	static boolean isPromotion(IBitboard destination) {
		return Bitboard.overlap(destination, Pawn.promotionMask);
	}

	static boolean isCapture(IBitboard destination, IBoard board) {
		return Bitboard.overlap(destination, board.getPieces(!board.getFen().isWhite()));
	}

	static boolean isEnPassant(IBitboard destination, IBoard board) {
		return Bitboard.overlap(destination, board.getFen().getEnPassantMask());
	}

	static boolean isKingCastle(Square source, Square destination, IBoard board) {
		return board.getFen().isWhite()
			? isWhiteKingCastle(source, destination)
			: isBlackKingCastle(source, destination);
	}

	static boolean isQueenCastle(Square source, Square destination, IBoard board) {
		return board.getFen().isWhite()
			? isWhiteQueenCastle(source, destination)
			: isBlackQueenCastle(source, destination);
	}

	static boolean canKingCastle(Square source, Square destination, IBoard board) {
		return board.getFen().isWhite()
			? whiteCanKingCastle(source, destination, board)
			: blackCanKingCastle(source, destination, board);
	}

	static boolean canQueenCastle(Square source, Square destination, IBoard board) {
		return board.getFen().isWhite()
			? whiteCanQueenCastle(source, destination, board)
			: blackCanQueenCastle(source, destination, board);
	}

	static boolean whiteCanKingCastle(Square source, Square destination, IBoard board) {
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
		return (
			source == CastleMove.whiteCastleSourceSquare &&
			destination == KingCastleMove.whiteKingCastleDestinationSquare
		);
	}

	private static boolean isBlackKingCastle(Square source, Square destination) {
		return (
			source == CastleMove.blackCastleSourceSquare &&
			destination == KingCastleMove.blackKingCastleDestinationSquare
		);
	}

	private static boolean isWhiteQueenCastle(Square source, Square destination) {
		return (
			source == CastleMove.whiteCastleSourceSquare &&
			destination == QueenCastleMove.whiteQueenCastleDestinationSquare
		);
	}

	private static boolean isBlackQueenCastle(Square source, Square destination) {
		return (
			source == CastleMove.blackCastleSourceSquare &&
			destination == QueenCastleMove.blackQueenCastleDestinationSquare
		);
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

	Move(Square source, Square destination, IBoard board) {
		checkValidMove(board, source);
		handleEnPassant(board);
		handleCastling(board, source);
		updateForsythEdwardsNotation(board);
		setSource(source);
		setDestination(destination);
	}

	private void checkValidMove(IBoard board, Square source) {
		if (!isPlayersPiece(board, source)) {
			throw new Error("Can not move opponent's piece");
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
				case h1, h8 -> fen.kingRookMove();
				case a1, a8 -> fen.queenRookMove();
			}
		}
	}

	private void updateForsythEdwardsNotation(IBoard board) {
		if (!board.getFen().isWhite()) {
			board.getFen().incrementFullMoveNumber();
		}
	}

	private boolean isPlayersPiece(IBoard board, Square source) {
		IBitboard player = board.getFen().isWhite()
			? board.getWhitePieces()
			: board.getBlackPieces();
		IBitboard movingPiece = Bitboard.getSingleBit(Board.getIndex(source));
		return Bitboard.overlap(player, movingPiece);
	}
}

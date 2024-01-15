package model.piece.king;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.fen.IForsythEdwardsNotation;
import model.move.IMove;
import model.move.Move;
import model.move.irreversible.capturing.CaptureMove;
import model.move.irreversible.castling.KingCastleMove;
import model.move.irreversible.castling.QueenCastleMove;
import model.move.reversible.QuietMove;
import model.piece.Movable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class King extends Piece implements Movable, Serializable {

	public King(Pieces variant) {
		super(variant);
	}

	public King(Pieces variant, IBitboard board) {
		super(variant, board);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		IBitboard eastAttack = Bitboard.shiftEast(piece);
		IBitboard westAttack = Bitboard.shiftWest(piece);
		IBitboard[] horizontals = new IBitboard[] { piece, eastAttack, westAttack };
		IBitboard horizontalAttack = Bitboard.mergeMany(horizontals);
		IBitboard northAttack = Bitboard.shiftNorth(horizontalAttack);
		IBitboard southAttack = Bitboard.shiftSouth(horizontalAttack);
		IBitboard[] all = new IBitboard[] { horizontalAttack, northAttack, southAttack };
		IBitboard allAttacks = Bitboard.mergeMany(all);
		return removeFriendlyPieces(allAttacks, board);
	}

	@Override
	protected boolean isInvalidMove(int source, int destination, IBoard board) {
		Square src = Square.getSquare(source);
		Square dst = Square.getSquare(destination);
		if (isValidKingCastle(src, dst, board)) {
			return false;
		}
		if (isValidQueenCastle(src, dst, board)) {
			return false;
		}
		return !(
			sourceSquareHasPiece(source) &&
			pieceCanMoveToDestination(source, destination, board) &&
			kingSafe(source, destination, board)
		);
	}

	private boolean isValidKingCastle(Square src, Square dst, IBoard board) {
		return Move.isKingCastle(src, dst, board) && Move.canKingCastle(src, dst, board);
	}

	private boolean isValidQueenCastle(Square src, Square dst, IBoard board) {
		return Move.isQueenCastle(src, dst, board) && Move.canQueenCastle(src, dst, board);
	}

	@Override
	protected IMove unsafeMove(int source, int destination, IBoard board) {
		Square src = Square.getSquare(source);
		Square dst = Square.getSquare(destination);
		IForsythEdwardsNotation fen = board.getFen();
		if (Move.isKingCastle(src, dst, board)) {
			return new KingCastleMove(src, dst, board);
		}
		if (Move.isQueenCastle(src, dst, board)) {
			return new QueenCastleMove(src, dst, board);
		}
		if (!fen.getCastling().equals("_")) {
			fen.kingMove();
		}
		if (Move.isCapture(Bitboard.getSingleBit(destination), board)) {
			return new CaptureMove(src, dst, board);
		}
		return new QuietMove(src, dst, board);
	}

	@Override
	protected boolean kingSafe(int source, int destination, IBoard board) {
		IBoard simulatedBoard = simulateMove(source, destination, board);
		boolean kingAttacked = Bitboard.overlap(
			simulatedBoard.getKing(board.getFen().getActiveColor() == 'w'),
			simulatedBoard.getAllOpponentAttacks()
		);
		if (kingAttacked) {
			throw new Error("King can not move into an attack");
		}
		return true;
	}

	@Override
	public IBitboard getAllAttacks(IBoard board) {
		return getAttacks(getBitboard(), board);
	}
}

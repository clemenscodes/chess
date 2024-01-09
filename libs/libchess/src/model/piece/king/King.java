package model.piece.king;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
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
	public IMove move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		return unsafeMove(source, destination, board);
	}

	@Override
	protected IMove unsafeMove(int source, int destination, IBoard board) {
		Square src = Square.getSquare(source);
		Square dst = Square.getSquare(destination);
		if (Move.isCapture(Bitboard.getSingleBit(destination), board)) {
			return new CaptureMove(src, dst, board);
		}
		if (Move.isKingCastle(Bitboard.getSingleBit(destination), board)) {
			return new KingCastleMove(src, dst, board);
		}
		if (Move.isQueenCastle(Bitboard.getSingleBit(destination), board)) {
			return new QueenCastleMove(src, dst, board);
		}
		return new QuietMove(src, dst, board);
	}

	@Override
	protected boolean kingSafe(int source, int destination, IBoard board) {
		IBoard simulatedBoard = simulateMove(source, destination, board);
		boolean kingAttacked = Bitboard.overlap(
			simulatedBoard.getOpponentKing(),
			simulatedBoard.getAllFriendlyAttacks()
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

package model.piece.knight;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.move.IMove;
import model.move.Moves;
import model.move.irreversible.capturing.CaptureMove;
import model.move.reversible.QuietMove;
import model.piece.Movable;
import model.piece.Piece;
import model.piece.Pieces;

public abstract class Knight extends Piece implements Movable, Serializable {

	public Knight(Pieces variant) {
		super(variant);
	}

	public IBitboard getAttacks(IBitboard piece, IBoard board) {
		IBitboard directions = Bitboard.mergeMany(
			new IBitboard[] {
				Bitboard.shiftEastEastNorth(piece),
				Bitboard.shiftEastEastSouth(piece),
				Bitboard.shiftWestWestNorth(piece),
				Bitboard.shiftWestWestSouth(piece),
				Bitboard.shiftNorthNorthEast(piece),
				Bitboard.shiftNorthNorthWest(piece),
				Bitboard.shiftSouthSouthEast(piece),
				Bitboard.shiftSouthSouthWest(piece),
			}
		);
		return removeFriendlyPieces(directions, board);
	}

	public IMove move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		Square src = Square.getSquare(source);
		Square dst = Square.getSquare(destination);
		if (Moves.isCapture(Bitboard.getSingleBit(destination), board)) {
			return new CaptureMove(src, dst, board, this);
		}
		return new QuietMove(src, dst, board, this);
	}
}

package model.piece.king;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;
import model.move.IMove;
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

	public IMove move(int source, int destination, IBoard board) {
		if (isInvalidMove(source, destination, board)) {
			throw new Error("Invalid move");
		}
		return new QuietMove(Square.getSquare(source), Square.getSquare(destination), board, this);
	}
}

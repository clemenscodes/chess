package model.piece.king;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
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
}

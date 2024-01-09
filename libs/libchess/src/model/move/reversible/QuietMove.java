package model.move.reversible;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

public class QuietMove extends ReversibleMove {

	public QuietMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		int src = Square.getIndex(source);
		int dst = Square.getIndex(destination);
		IPiece piece = board.getPiece(board.getPieceByIndex(Square.getIndex(source)));
		piece.getBitboard().toggleBits(piece.getMoveMask(src, dst));
	}
}

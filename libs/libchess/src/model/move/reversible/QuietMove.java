package model.move.reversible;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

public class QuietMove extends ReversibleMove {

	public QuietMove(Square source, Square destination, IBoard board, IPiece movingPiece) {
		super(source, destination, board);
		var src = Square.getIndex(source);
		var dst = Square.getIndex(destination);
		movingPiece.getBitboard().toggleBits(movingPiece.getMoveMask(src, dst));
	}
}

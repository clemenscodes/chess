package model.move.irreversible.capturing;

import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.IrreversibleMove;
import model.piece.IPiece;

public class CaptureMove extends IrreversibleMove {

	public CaptureMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		int src = Square.getIndex(source);
		int dst = Square.getIndex(destination);
		board.capturePiece(Square.getIndex(destination));
		IPiece piece = board.getPiece(board.getPieceByIndex(Square.getIndex(source)));
		piece.getBitboard().toggleBits(piece.getMoveMask(src, dst));
	}
}

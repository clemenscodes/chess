package move.irreversible.capturing;

import board.Board;
import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;
import move.irreversible.IrreversibleMove;

public class CaptureMove extends IrreversibleMove {

	public CaptureMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		int src = Board.getIndex(source);
		int dst = Board.getIndex(destination);
		board.capturePiece(Board.getIndex(destination));
		IPiece piece = board.getPiece(source);
		piece.getBitboard().toggleBits(piece.getMoveMask(src, dst));
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

package model.move.irreversible.capturing;

import model.bits.Bitboard;
import model.board.IBoard;
import model.board.Square;
import model.move.irreversible.IrreversibleMove;
import model.piece.IPiece;
import model.piece.Pieces;

public class CaptureMove extends IrreversibleMove {

	public CaptureMove(
		Square source,
		Square destination,
		IBoard board,
		IPiece capturingPiece,
		Pieces capturedPiece
	) {
		super(source, destination, board);
		int src = Square.getIndex(source);
		int dst = Square.getIndex(destination);
		board.getPiece(capturedPiece).getBitboard().toggleBits(Bitboard.getSingleBit(dst));
		capturingPiece.getBitboard().toggleBits(capturingPiece.getMoveMask(src, dst));
	}
}
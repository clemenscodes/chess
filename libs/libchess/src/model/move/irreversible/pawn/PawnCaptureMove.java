package model.move.irreversible.pawn;

import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;

public class PawnCaptureMove extends PawnMove {

	private IPiece capturedPiece;

	public PawnCaptureMove(Square source, Square destination, IBoard board, IPiece pawn) {
		super(source, destination, board);
		board.capturePiece(Square.getIndex(destination));
		pawn.getBitboard().setBitByIndex(Square.getIndex(destination));
		setCapturedPiece(board.getPiece(destination));
	}

	private IPiece getCapturedPiece() {
		return capturedPiece;
	}

	private void setCapturedPiece(IPiece capturedPiece) {
		this.capturedPiece = capturedPiece;
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

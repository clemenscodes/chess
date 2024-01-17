package move.irreversible.pawn;

import board.Board;
import model.board.IBoard;
import model.board.Square;
import model.piece.IPiece;
import piece.pawn.WhitePawn;

public class EnPassantCaptureMove extends PawnMove {

	public EnPassantCaptureMove(Square source, Square destination, IBoard board, IPiece pawn) {
		super(source, destination, board);
		int dst = Board.getIndex(destination);
		int passedSquareIndex = getPassedSquareIndex(dst, pawn);
		board.capturePiece(passedSquareIndex);
		pawn.getBitboard().setBitByIndex(dst);
	}

	private int getPassedSquareIndex(int destination, IPiece pawn) {
		return pawn instanceof WhitePawn ? destination + Board.SOUTH : destination + Board.NORTH;
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

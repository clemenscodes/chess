package model.move;

import java.io.Serializable;
import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;

public abstract class Move implements IMove, Serializable {

	private Square source;
	private Square destination;

	public Move(Square source, Square destination, IBoard board) {
		if (!isPlayersPiece(board, source)) {
			throw new Error("Can not move opponents piece");
		}
		setSource(source);
		setDestination(destination);
		var fen = board.getFen();
		fen.incrementFullMoveNumber();
		fen.switchActiveColor();
	}

	public Square getSource() {
		return source;
	}

	public Square getDestination() {
		return destination;
	}

	private void setSource(Square source) {
		this.source = source;
	}

	private void setDestination(Square destination) {
		this.destination = destination;
	}

	private boolean isPlayersPiece(IBoard board, Square source) {
		IBitboard player = board.getFen().getActiveColor() == 'w'
			? board.getWhitePieces()
			: board.getBlackPieces();
		IBitboard movingPiece = Bitboard.getSingleBit(Square.getIndex(source));
		return Bitboard.overlap(player, movingPiece);
	}
}

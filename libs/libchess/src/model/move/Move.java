package model.move;

import java.io.Serializable;
import model.board.IBoard;
import model.board.Square;

public abstract class Move implements IMove, Serializable {

	private Square source;
	private Square destination;

	public Move(Square source, Square destination, IBoard board) {
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
}

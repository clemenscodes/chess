package model.move;

import java.io.Serializable;
import model.board.Square;

public class Move implements IMove, Serializable {

	private Square source;
	private Square destination;

	public Move(Square source, Square destination) {
		setSource(source);
		setDestination(destination);
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

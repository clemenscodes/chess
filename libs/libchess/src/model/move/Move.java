package model.move;

import java.io.Serializable;
import model.board.Square;

public abstract class Move implements IMove, Serializable {

	private Square source;
	private Square destination;
	private Moves kind;

	public Move(Square source, Square destination, Moves kind) {
		setSource(source);
		setDestination(destination);
		setKind(kind);
	}

	public Square getSource() {
		return source;
	}

	public Square getDestination() {
		return destination;
	}

	public Moves getKind() {
		return kind;
	}

	private void setSource(Square source) {
		this.source = source;
	}

	private void setDestination(Square destination) {
		this.destination = destination;
	}

	private void setKind(Moves kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		return String.valueOf(getSource()) + getDestination();
	}
}

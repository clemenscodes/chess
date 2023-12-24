package model.move;

import model.board.Square;

public interface IMove {
	Square getSource();

	Square getDestination();
}

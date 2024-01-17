package api.model.move;

import api.model.board.Square;

public interface IMove {
	Square getSource();

	Square getDestination();
}

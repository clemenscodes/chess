package model.move;

import model.Square;

public interface IMove {
	Square getSource();
	Square getDestination();
}

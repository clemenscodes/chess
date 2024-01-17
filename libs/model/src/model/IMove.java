package model;

import api.model.Square;

interface IMove {
	Square getSource();

	Square getDestination();
}

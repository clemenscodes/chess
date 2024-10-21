package model;

import api.Square;

interface IMove {
    Square getSource();

    Square getDestination();
}

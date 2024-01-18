package model;

import api.model.Pieces;
import api.model.Square;
import java.io.Serializable;
import java.util.ArrayList;

interface IPiece extends Serializable {
	Pieces getVariant();

	IBitboard getBitboard();

	IBitboard getMoveMask(int source, int destination);

	IBitboard getAttacks(IBitboard piece, IBoard board);

	IBitboard getAllAttacks(IBoard board);

	ArrayList<Square[]> getMoves(IBoard board);

	boolean isInvalidMove(int source, int destination, IBoard board);
}
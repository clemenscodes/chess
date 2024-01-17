package model.piece;

import java.io.Serializable;
import java.util.ArrayList;
import model.bits.IBitboard;
import model.board.IBoard;
import model.board.Square;

public interface IPiece extends Serializable {
	Pieces getVariant();

	IBitboard getBitboard();

	IBitboard getMoveMask(int source, int destination);

	IBitboard getAttacks(IBitboard piece, IBoard board);

	IBitboard getAllAttacks(IBoard board);

	ArrayList<Square[]> getMoves(IBoard board);

	boolean isInvalidMove(int source, int destination, IBoard board);
}

package api.model.piece;

import api.model.bits.IBitboard;
import api.model.board.IBoard;
import api.model.board.Square;
import java.io.Serializable;
import java.util.ArrayList;

public interface IPiece extends Serializable {
	Pieces getVariant();

	IBitboard getBitboard();

	IBitboard getMoveMask(int source, int destination);

	IBitboard getAttacks(IBitboard piece, IBoard board);

	IBitboard getAllAttacks(IBoard board);

	ArrayList<Square[]> getMoves(IBoard board);

	boolean isInvalidMove(int source, int destination, IBoard board);
}

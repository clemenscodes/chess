import api.model.board.IBoard;
import api.model.board.Square;

abstract class PawnMove extends IrreversibleMove {

	PawnMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		board.getPiece(source).getBitboard().unsetBitByIndex(Board.getIndex(source));
	}
}

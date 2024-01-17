import api.model.board.IBoard;
import api.model.board.Square;
import api.model.piece.IPiece;

class QuietMove extends ReversibleMove {

	QuietMove(Square source, Square destination, IBoard board) {
		super(source, destination, board);
		int src = Board.getIndex(source);
		int dst = Board.getIndex(destination);
		IPiece piece = board.getPiece(source);
		piece.getBitboard().toggleBits(piece.getMoveMask(src, dst));
	}

	@Override
	public String toString() {
		Square source = getSource();
		Square destination = getDestination();
		return String.valueOf(source) + destination;
	}
}

package data.board;

import data.piece.Piece;
import java.io.Serializable;

public class Field implements Serializable {

	private Piece piece;

	public Field(Piece piece) {
		setPiece(piece);
	}

	public Piece getPiece() {
		return piece;
	}

	private void setPiece(Piece piece) {
		this.piece = piece;
	}
}

package model.board;

import java.io.Serializable;
import model.piece.Piece;

public class Field implements Serializable {

	private Piece piece;

	public Field(Piece piece) {
		setPiece(piece);
	}

	public Piece getPiece() {
		return piece;
	}

	void setPiece(Piece piece) {
		this.piece = piece;
	}
}

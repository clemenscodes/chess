package model.piece.queen.extension;

import java.io.Serializable;
import model.piece.Pieces;
import model.piece.queen.Queen;

public class WhiteQueen extends Queen implements Serializable {

	public WhiteQueen() {
		super(Pieces.WhiteQueen, '♕');
	}
}

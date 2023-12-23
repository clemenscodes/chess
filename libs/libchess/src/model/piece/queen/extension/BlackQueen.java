package model.piece.queen.extension;

import java.io.Serializable;
import model.piece.Pieces;
import model.piece.queen.Queen;

public class BlackQueen extends Queen implements Serializable {

	public static final char SYMBOL = '♛';

	public BlackQueen() {
		super(Pieces.BlackQueen);
	}
}

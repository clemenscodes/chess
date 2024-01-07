package model.piece.queen;

import java.io.Serializable;
import model.piece.Pieces;

public class BlackQueen extends Queen implements Serializable {

	public static final char SYMBOL = '♛';

	public BlackQueen() {
		super(Pieces.BlackQueen);
	}
}

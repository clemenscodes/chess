package model.piece.queen;

import java.io.Serializable;
import model.piece.Pieces;

public class WhiteQueen extends Queen implements Serializable {

	public static final char SYMBOL = '♕';

	public WhiteQueen() {
		super(Pieces.WhiteQueen);
	}
}

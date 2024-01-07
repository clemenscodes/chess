package model.piece.bishop;

import java.io.Serializable;
import model.piece.Pieces;

public class BlackBishop extends Bishop implements Serializable {

	public static final char SYMBOL = '♝';

	public BlackBishop() {
		super(Pieces.BlackBishop);
	}
}

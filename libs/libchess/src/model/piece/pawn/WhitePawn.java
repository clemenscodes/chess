package model.piece.pawn;

import java.io.Serializable;
import model.piece.Pieces;

public class WhitePawn extends Pawn implements Serializable {

	public static final char SYMBOL = '♙';

	public WhitePawn() {
		super(Pieces.WhitePawn);
	}
}

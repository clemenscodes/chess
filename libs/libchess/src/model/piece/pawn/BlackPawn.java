package model.piece.pawn;

import java.io.Serializable;
import model.piece.Pieces;

public class BlackPawn extends Pawn implements Serializable {

	public static final char SYMBOL = '♟';

	public BlackPawn() {
		super(Pieces.BlackPawn);
	}
}

package model.piece.pawn.extension;

import java.io.Serializable;
import model.piece.Pieces;
import model.piece.pawn.Pawn;

public class WhitePawn extends Pawn implements Serializable {

	public WhitePawn() {
		super(Pieces.WhitePawn, '♙');
	}
}

package model.piece.bishop.extension;

import java.io.Serializable;
import model.piece.Pieces;
import model.piece.bishop.Bishop;

public class BlackBishop extends Bishop implements Serializable {

	public BlackBishop() {
		super(Pieces.BlackBishop, '♝');
	}
}

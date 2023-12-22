package model.piece.knight.extension;

import java.io.Serializable;
import model.piece.Pieces;
import model.piece.knight.Knight;

public class WhiteKnight extends Knight implements Serializable {

	public WhiteKnight() {
		super(Pieces.WhiteKnight, '♘');
	}
}

package model.piece.knight.extension;

import java.io.Serializable;
import model.piece.Pieces;
import model.piece.knight.Knight;

public class BlackKnight extends Knight implements Serializable {

	public BlackKnight() {
		super(Pieces.BlackKnight, '♞');
	}
}

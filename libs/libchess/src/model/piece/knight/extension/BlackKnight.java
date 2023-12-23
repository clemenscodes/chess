package model.piece.knight.extension;

import java.io.Serializable;
import model.piece.Pieces;
import model.piece.knight.Knight;

public class BlackKnight extends Knight implements Serializable {

	public static final char SYMBOL = '♞';

	public BlackKnight() {
		super(Pieces.BlackKnight);
	}
}

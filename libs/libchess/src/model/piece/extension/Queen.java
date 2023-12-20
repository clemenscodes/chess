package model.piece.extension;

import java.io.Serializable;
import model.enums.Color;
import model.piece.Piece;
import model.piece.Pieces;

public class Queen extends Piece implements Serializable {

	public Queen(Color color, int position, int id) {
		super(color, Pieces.Queen, 9, position, id);
		setSymbol(getColor() == Color.White ? '♕' : '♛');
	}

	@Override
	public boolean isValidMove(int position) {
		return false;
	}
}

package model.piece.extension;

import java.io.Serializable;
import model.enums.Color;
import model.piece.Piece;
import model.piece.Pieces;

public class Rook extends Piece implements Serializable {

	public Rook(Color color, int position, int id) {
		super(color, Pieces.Rook, 5, position, id);
		setSymbol(getColor() == Color.White ? '♖' : '♜');
	}

	@Override
	public boolean isValidMove(int position) {
		return false;
	}
}

package model.board;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import model.piece.Piece;
import model.player.Black;
import model.player.Player;
import model.player.White;

public class Board implements Serializable {

	private Field[] fields;
	private White white;
	private Black black;

	public static int getRowIndex(int row) {
		if (row >= 1 && row <= 8) {
			return ((row - 1) * 8);
		} else {
			throw new Error("Row does not exist");
		}
	}

	public Board() {
		setWhite(new White());
		setBlack(new Black());
		initializeFields();
	}

	private void initializeFields() {
		var fields = new Field[8 * 8];
		addPiecesToField(getWhite(), fields);
		addPiecesToField(getBlack(), fields);
		for (int i = 16; i < 56; i++) {
			fields[i] = new Field(null);
		}
		setFields(fields);
	}

	private void addPiecesToField(Player player, Field[] fields) {
		ArrayList<Piece> allPieces = new ArrayList<>();
		allPieces.addAll(player.getPawns());
		allPieces.addAll(player.getBishops());
		allPieces.addAll(player.getKnights());
		allPieces.addAll(player.getRooks());
		allPieces.addAll(player.getQueens());
		allPieces.add(player.getKing());
		for (var p : allPieces) {
			fields[p.getPosition() - 1] = new Field(p);
		}
	}

	public Field[] getFields() {
		return fields;
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	public White getWhite() {
		return white;
	}

	public Black getBlack() {
		return black;
	}

	private void setWhite(White white) {
		this.white = white;
	}

	private void setBlack(Black black) {
		this.black = black;
	}
}

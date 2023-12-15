package model.board;

import java.io.Serializable;
import model.player.Black;
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
		setFields(new Field[8 * 8]);
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

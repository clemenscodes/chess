package data.board;

import java.io.Serializable;

public class Board implements Serializable {

	private Field[] fields;

	public Board(Field[] fields) {
		setFields(fields);
	}

	public Field[] getFields() {
		return fields;
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	public static int getRowIndex(int row) {
		if (row >= 1 && row <= 8) {
			return ((row - 1) * 8);
		} else {
			throw new Error("Row does not exist");
		}
	}
}

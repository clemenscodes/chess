package model.data.board;

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
}

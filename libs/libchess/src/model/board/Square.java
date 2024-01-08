package model.board;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// LERF (Little Endian Rank-File Mapping)
public enum Square implements Serializable {
	a1,
	b1,
	c1,
	d1,
	e1,
	f1,
	g1,
	h1,
	a2,
	b2,
	c2,
	d2,
	e2,
	f2,
	g2,
	h2,
	a3,
	b3,
	c3,
	d3,
	e3,
	f3,
	g3,
	h3,
	a4,
	b4,
	c4,
	d4,
	e4,
	f4,
	g4,
	h4,
	a5,
	b5,
	c5,
	d5,
	e5,
	f5,
	g5,
	h5,
	a6,
	b6,
	c6,
	d6,
	e6,
	f6,
	g6,
	h6,
	a7,
	b7,
	c7,
	d7,
	e7,
	f7,
	g7,
	h7,
	a8,
	b8,
	c8,
	d8,
	e8,
	f8,
	g8,
	h8;

	private static final Square[] SQUARES = new Square[] {
		a1,
		b1,
		c1,
		d1,
		e1,
		f1,
		g1,
		h1,
		a2,
		b2,
		c2,
		d2,
		e2,
		f2,
		g2,
		h2,
		a3,
		b3,
		c3,
		d3,
		e3,
		f3,
		g3,
		h3,
		a4,
		b4,
		c4,
		d4,
		e4,
		f4,
		g4,
		h4,
		a5,
		b5,
		c5,
		d5,
		e5,
		f5,
		g5,
		h5,
		a6,
		b6,
		c6,
		d6,
		e6,
		f6,
		g6,
		h6,
		a7,
		b7,
		c7,
		d7,
		e7,
		f7,
		g7,
		h7,
		a8,
		b8,
		c8,
		d8,
		e8,
		f8,
		g8,
		h8,
	};

	private static final Map<Square, Integer> SQUARE_TO_INDEX = new HashMap<>();
	private static final Map<Integer, Integer> INDEX_TO_INDEX = new HashMap<>();

	static {
		for (int i = 0; i < SQUARES.length; i++) {
			SQUARE_TO_INDEX.put(getSquare(i), i);
		}
		INDEX_TO_INDEX.get(99);
	}

	public static Square getSquare(int index) {
		return SQUARES[index];
	}

	public static int getIndex(Square square) {
		return SQUARE_TO_INDEX.get(square);
	}
}

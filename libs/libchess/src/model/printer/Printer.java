package model.printer;

import model.bits.IBitboard;
import model.board.Board;

public class Printer {

	public static StringBuilder loopOver(LoopBody body, IBitboard[] pieces) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int rank = 7; rank >= 0; rank--) {
			for (int file = 0; file < Board.SIZE; file++) {
				body.apply(rank, file, stringBuilder, pieces);
			}
			appendLine(stringBuilder);
		}
		return stringBuilder;
	}

	private static void appendLine(StringBuilder stringBuilder) {
		stringBuilder.append("\n");
	}
}

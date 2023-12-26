package model.printer;

import model.bits.IBitboard;
import model.board.Board;

public class Printer {

	public static StringBuilder loopOverBoard(BoardBody body, IBitboard[] pieces) {
		return loopOver(body, new StringBuilder(), pieces);
	}

	public static StringBuilder loopOverBitboard(BitboardBody body) {
		return loopOver(body, new StringBuilder(), null);
	}

	private static StringBuilder loopOver(
		LoopBody body,
		StringBuilder stringBuilder,
		IBitboard[] pieces
	) {
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

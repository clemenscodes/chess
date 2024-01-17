import api.model.bits.IBitboard;

class Writer {

	static StringBuilder loopOver(LoopBody body, IBitboard[] pieces) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int rank = 7; rank >= 0; rank--) {
			for (int file = 0; file < Board.SIZE; file++) {
				body.apply(rank, file, stringBuilder, pieces);
			}
			if (rank != 0) {
				appendLine(stringBuilder);
			}
		}
		return stringBuilder;
	}

	private static void appendLine(StringBuilder stringBuilder) {
		stringBuilder.append("\n");
	}
}

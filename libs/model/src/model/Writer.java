package model;

import java.util.concurrent.BlockingQueue;

class Writer<T> implements IWriter<T> {

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

	private BlockingQueue<T> sharedQueue;

	Writer(BlockingQueue<T> sharedQueue) {
		setSharedQueue(sharedQueue);
	}

	public void write(T data) {
		while (getSharedQueue().peek() != null) {
			getSharedQueue().remove();
		}
		getSharedQueue().add(data);
	}

	private BlockingQueue<T> getSharedQueue() {
		return sharedQueue;
	}

	private void setSharedQueue(BlockingQueue<T> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}
}

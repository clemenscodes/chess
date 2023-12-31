package model.util.io.reader;

import java.util.concurrent.BlockingQueue;

public class Reader implements IReader {

	private BlockingQueue<String> queue;

	public Reader(BlockingQueue<String> queue) {
		setQueue(queue);
	}

	public String readLine() {
		return getQueue().poll();
	}

	private BlockingQueue<String> getQueue() {
		return queue;
	}

	private void setQueue(BlockingQueue<String> queue) {
		this.queue = queue;
	}
}

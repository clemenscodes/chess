package model;

import api.model.IReader;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Reader implements IReader {

	private Scanner scanner;
	private Queue<String> buffer;

	Reader(InputStream in) {
		setScanner(in);
		setBuffer(new LinkedList<>());
	}

	public String readLine() {
		if (!getBuffer().isEmpty()) {
			return getBuffer().poll();
		}
		if (getScanner().hasNextLine()) {
			String line = getScanner().nextLine();
			getBuffer().add(line);
			return line;
		}
		return "";
	}

	private Scanner getScanner() {
		return scanner;
	}

	private void setScanner(InputStream in) {
		scanner = new Scanner(in);
	}

	private Queue<String> getBuffer() {
		return buffer;
	}

	private void setBuffer(Queue<String> buffer) {
		this.buffer = buffer;
	}
}

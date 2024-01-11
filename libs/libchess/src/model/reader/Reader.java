package model.reader;

import java.io.InputStream;
import java.util.Scanner;

public class Reader implements IReader {

	private Scanner scanner;

	public Reader(InputStream in) {
		setScanner(in);
	}

	public String readLine() {
		return getScanner().hasNextLine() ? getScanner().nextLine() : "";
	}

	private Scanner getScanner() {
		return scanner;
	}

	private void setScanner(InputStream in) {
		scanner = new Scanner(in);
	}
}

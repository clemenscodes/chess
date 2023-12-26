package model.util.io.reader;

import java.io.InputStream;
import java.util.Scanner;

public class Reader implements Readable {

	private Scanner scanner;

	public Reader(InputStream input) {
		setScanner(input);
	}

	public String readLine() {
		return getScanner().nextLine();
	}

	private void setScanner(InputStream input) {
		this.scanner = new Scanner(input);
	}

	private Scanner getScanner() {
		return scanner;
	}
}

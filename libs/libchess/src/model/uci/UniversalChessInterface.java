package model.uci;

import java.io.InputStream;
import model.util.io.reader.IReader;
import model.util.io.reader.Reader;

public class UniversalChessInterface implements IUniversalChessInterface {

	private IReader reader;

	public UniversalChessInterface(InputStream input) {
		setReader(input);
	}

	public UniversalChessInterface() {
		setReader(System.in);
	}

	private IReader getReader() {
		return reader;
	}

	private void setReader(InputStream input) {
		this.reader = new Reader(input);
	}

	@Override
	public void run() {
		while (true) {
			String input = getReader().readLine();
			if (input == null) {
				break;
			}
			System.out.println("Received: " + input);
		}
	}
}

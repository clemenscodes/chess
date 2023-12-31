package model.uci;

import model.util.io.reader.IReader;

public class UniversalChessInterface implements IUniversalChessInterface {

	private IReader reader;

	public UniversalChessInterface(IReader reader) {
		setReader(reader);
	}

	private IReader getReader() {
		return reader;
	}

	private void setReader(IReader reader) {
		this.reader = reader;
	}

	@Override
	public void run() {
		while (true) {
			String input = getReader().readLine();
			if (input == null) {
				continue;
			}
			if (input.equals("stop")) {
				break;
			}
			System.out.println("Received: " + input);
		}
	}
}

package model.util.io.reader;

import java.io.InputStream;
import java.util.Scanner;

public class Reader {

	public static String readLine(InputStream input) {
		return new Scanner(input).nextLine();
	}
}

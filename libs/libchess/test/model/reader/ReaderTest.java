package model.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class ReaderTest {

	@Test
	void shouldReadLineCorrectly() {
		String inputString = "Hello, World!";
		InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
		Reader reader = new Reader(inputStream);
		String result = reader.readLine();
		assertEquals(inputString, result.trim());
	}

	@Test
	void shouldReadEmptyLineCorrectly() {
		String inputString = "";
		InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
		Reader reader = new Reader(inputStream);
		String result = reader.readLine();
		assertEquals(inputString, result);
	}

	@Test
	void shouldReadFromBufferWhenNotEmpty() {
		String inputString = "First Line\nSecond Line";
		InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
		Reader reader = new Reader(inputStream);
		reader.readLine();
		reader.readLine();
		String result = reader.readLine();
		assertEquals("Second Line", result.trim());
	}

	@Test
	void shouldReadFromStreamWhenBufferEmpty() {
		String inputString = "First Line\nSecond Line";
		InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
		Reader reader = new Reader(inputStream);
		String result = reader.readLine();
		assertEquals("First Line", result.trim());
	}
}

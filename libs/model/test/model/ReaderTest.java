package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderTest {

    public BlockingQueue<String> queue;
    private IWriter<String> writer;
    private IReader<String> reader;

    @BeforeEach
    void setup() {
        queue = new LinkedBlockingQueue<>();
        writer = new Writer<>(queue);
        reader = new Reader<>(queue);
    }

    @Test
    void shouldReadLineCorrectly() {
        String inputString = "Hello, World!";
        writer.write(inputString);
        String result = reader.read();
        assertEquals(inputString, result.trim());
    }

    @Test
    void shouldReadEmptyLineCorrectly() {
        String inputString = "";
        writer.write(inputString);
        String result = reader.read();
        assertEquals(inputString, result);
    }
}

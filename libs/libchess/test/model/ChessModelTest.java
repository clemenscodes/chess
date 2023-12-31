package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Test;

public class ChessModelTest {

	@Test
	void shouldInitialize() {
		assertDoesNotThrow(() -> new ChessModel());
		assertDoesNotThrow(() -> new ChessModel(new LinkedBlockingQueue<>()));
	}
}

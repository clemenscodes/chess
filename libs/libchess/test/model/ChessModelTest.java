package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ChessModelTest {

	@Test
	void shouldInitialize() {
		assertDoesNotThrow(ChessModel::new);
	}
}

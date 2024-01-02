package model.board;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import model.fen.ForsythEdwardsNotation;
import org.junit.jupiter.api.Test;

public class BoardTest {

	@Test
	void shouldInitialize() {
		assertDoesNotThrow(() -> new Board());
		assertDoesNotThrow(() -> new Board(new ForsythEdwardsNotation()));
		System.out.println(Board.firstFile);
		System.out.println(Board.notFirstFile);
		System.out.println(Board.secondFile);
		System.out.println(Board.lastFile);
		System.out.println(Board.notLastFile);
		System.out.println(Board.secondLastFile);
		System.out.println(Board.firstRank);
		System.out.println(Board.notFirstRank);
		System.out.println(Board.secondRank);
		System.out.println(Board.thirdRank);
		System.out.println(Board.fourthRank);
		System.out.println(Board.fifthRank);
		System.out.println(Board.sixthRank);
		System.out.println(Board.seventhRank);
		System.out.println(Board.eighthRank);
		System.out.println(Board.notEighthRank);
		System.out.println(Board.diagonal);
		System.out.println(Board.antiDiagonal);
		System.out.println(Board.lightSquares);
		System.out.println(Board.darkSquares);
	}
}

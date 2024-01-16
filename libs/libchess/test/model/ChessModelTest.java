package model;

import static model.board.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import model.board.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChessModelTest {

	private ChessModel chessModel;

	@BeforeEach
	void setUp() {
		chessModel = new ChessModel();
	}

	@Test
	void startGameShouldSetInitialState() {
		chessModel.startGame();
		assertEquals(State.Start, chessModel.getGameState());
		assertEquals(0, chessModel.getMoveList().getPlayedMoves());
	}

	@Test
	void startNewGameShouldResetGame() {
		chessModel.startNewGame();
		assertEquals(State.Playing, chessModel.getGameState());
		assertEquals(0, chessModel.getMoveList().getPlayedMoves());
	}

	@Test
	void makeMoveShouldUpdateGameStateAndPrintGame() {
		String[] moves = { "e2", "e4" };
		InputStream inputStream = new ByteArrayInputStream(String.join("\n", moves).getBytes());
		chessModel = new ChessModel(inputStream);
		chessModel.startNewGame();
		chessModel.makeMove(Square.e2, Square.e4);
		assertEquals(State.Playing, chessModel.getGameState());
		assertEquals(1, chessModel.getMoveList().getPlayedMoves());
	}

	@Test
	void shouldSetGameStateToCheckmate() {
		chessModel.startNewGame();
		chessModel.makeMove(g2, g4);
		chessModel.makeMove(e7, e5);
		chessModel.makeMove(f2, f3);
		chessModel.makeMove(d8, h4);
		assertEquals(State.Checkmate, chessModel.getGameState());
	}
}

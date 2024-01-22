package model;

import static api.model.Square.*;
import static org.junit.jupiter.api.Assertions.*;

import api.model.Square;
import api.model.State;
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
		chessModel = new ChessModel();
		chessModel.startNewGame();
		chessModel.makeMove(Square.e2, Square.e4);
		chessModel.gameOver();
		chessModel.joinMoveThread();
		assertEquals(State.Start, chessModel.getGameState());
		assertEquals(1, chessModel.getMoveList().getPlayedMoves());
	}

	@Test
	void shouldSetGameStateToCheckmate() {
		chessModel.startNewGame();
		chessModel.makeMove(g2, g4);
		chessModel.makeMove(e7, e5);
		chessModel.makeMove(f2, f3);
		chessModel.makeMove(d8, h4);
		chessModel.joinMoveThread();
		assertEquals(State.Checkmate, chessModel.getGameState());
	}

	@Test
	void shouldDetermineNonCheckmate() {
		chessModel.startGame();
		assertNotSame(chessModel.getGameState(), State.Checkmate);
	}

	@Test
	void shouldDetermineCheckmate() {
		String fen = "rnb1kbnr/pppp1ppp/8/4p3/6Pq/5P2/PPPPP2P/RNBQKBNR w KQkq - 1 3";
		chessModel.startGame(fen);
		assertTrue(chessModel.isCheckmate());
	}

	@Test
	void shouldNotDetermineStalemate() {
		String fen = "r1bqk1nr/pppp1Qpp/2n5/2b1p3/2B1P3/8/PPPP1PPP/RNB1K1NR b KQkq - 0 4";
		chessModel.startGame(fen);
		assertFalse(chessModel.isStalemate());
	}

	@Test
	void shouldDetermineStalemate() {
		String fen = "8/8/8/8/8/7k/7p/7K w - - 0 1";
		chessModel.startGame(fen);
		assertTrue(chessModel.isStalemate());
	}
}

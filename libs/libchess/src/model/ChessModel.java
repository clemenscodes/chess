package model;

import model.board.Board;

public class ChessModel implements IChessModel {

	private GameState state;
	private Board board;

	public void startGame(int width, int height) {
		setGameState(GameState.Start);
		setBoard(new Board());
	}

	public void startNewGame(int width, int height) {
		setGameState(GameState.Playing);
	}

	public GameState getGameState() {
		return state;
	}

	public Board getBoard() {
		return board;
	}

	private void setBoard(Board board) {
		this.board = board;
	}

	private void setGameState(GameState state) {
		this.state = state;
	}
}

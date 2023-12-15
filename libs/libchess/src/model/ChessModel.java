package model;

import model.board.Board;
import model.player.Black;
import model.player.White;

public class ChessModel implements IChessModel {

	private GameState state;
	private White white;
	private Black black;
	private Board board;

	public void startGame(int width, int height) {
		setGameState(GameState.START);
		setBoard(new Board());
		setWhite(new White());
		setBlack(new Black());
	}

	public void startNewGame(int width, int height) {
		setGameState(GameState.PLAYING);
	}

	public Board getBoard() {
		return board;
	}

	private void setBoard(Board board) {
		this.board = board;
	}

	public GameState getGameState() {
		return state;
	}

	public White getWhite() {
		return white;
	}

	public Black getBlack() {
		return black;
	}

	private void setGameState(GameState state) {
		this.state = state;
	}

	private void setWhite(White white) {
		this.white = white;
	}

	private void setBlack(Black black) {
		this.black = black;
	}
}

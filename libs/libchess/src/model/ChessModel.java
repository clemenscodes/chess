package model;

import model.data.GameState;
import model.data.board.Board;
import model.data.board.Field;
import model.data.player.Black;
import model.data.player.White;
import model.data.unit.Unit;

public class ChessModel {

	private GameState state;
	private White white;
	private Black black;
	private Board board;

	public void startGame(int width, int height) {
		setGameState(GameState.START);
		setBoard(new Board(new Field[8 * 8]));
		setWhite(new White(new Unit[16]));
		setBlack(new Black(new Unit[16]));
	}

	public void startNewGame(int width, int height) {
		setGameState(GameState.PLAYING);
	}

	public void moveWhite(float distance) {
		return;
	}

	public void moveBlack(float distance) {
		return;
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

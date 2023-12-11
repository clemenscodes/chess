package controller;

import model.TicTacToeModel;
import view.ITicTacToeView;

public class TicTacToeController implements ITicTacToeController {

	private TicTacToeModel model;
	private ITicTacToeView view;
	private char winner = '*';
	private boolean errorFlag = false;
	private String errorMessage;

	public TicTacToeController() {}

	public void setModel(TicTacToeModel model) {
		this.model = model;
	}

	public void setView(ITicTacToeView view) {
		this.view = view;
	}

	public void calculateWinner() {
		if (model.hasPlayer1Won()) {
			this.winner = model.PLAYER_1;
			System.out.println("Player 1 won");
			return;
		}
		if (model.hasPlayer2Won()) {
			this.winner = model.PLAYER_2;
			System.out.println("Player 2 won");
			return;
		}
		this.winner = '/';
	}

	public boolean hasError() {
		return this.errorFlag;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void resetError() {
		this.errorFlag = false;
		this.errorMessage = null;
	}

	public char getWinner() {
		return this.winner;
	}

	@Override
	public void nextFrame() {
		view.drawGame();
	}

	@Override
	public void userInput(int x, int y) {
		if (this.errorFlag) {
			this.resetError();
			return;
		}
		if (model.isGameOver()) {
			restart();
			return;
		}
		int size = this.view.getSize();
		int field = model.getFieldFromCoordinates(x, y, size);
		try {
			model.move(field);
		} catch (Exception e) {
			this.errorFlag = true;
			this.errorMessage = e.getMessage();
		}
		if (model.isGameOver()) {
			calculateWinner();
		}
	}

	@Override
	public char[] getBoard() {
		return model.getBoard();
	}

	@Override
	public void restart() {
		System.out.println("Restarting game...");
		this.winner = '*';
		model.newGame();
	}
}

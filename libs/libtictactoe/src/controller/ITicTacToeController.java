package controller;

public interface ITicTacToeController {
	void nextFrame();

	void userInput(int x, int y);

	char[] getBoard();

	boolean hasError();

	String getErrorMessage();

	char getWinner();

	void restart();
}

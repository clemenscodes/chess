package controller;

import api.controller.IChessController;
import api.model.IChessModel;
import api.model.Square;
import api.model.State;
import api.view.IChessView;

public class ChessController implements IChessController {

	private IChessModel model;
	private IChessView view;

	public void setModel(IChessModel model) {
		this.model = model;
	}

	public void setView(IChessView view) {
		this.view = view;
	}

	public void startGame() {
		getModel().startGame();
	}

	public void startNewGame() {
		getModel().startNewGame();
	}

	public void nextFrame() {
		State state = getModel().getGameState();
		getView().setBackground();
		switch (state) {
			case Start -> getView().drawStart();
			case Playing -> getView().drawPlaying();
			case Checkmate -> getView().drawCheckmate();
			case Stalemate -> getView().drawStalemate();
			case GameOver -> getView().drawGameOver();
			default -> throw new IllegalStateException("Unexpected value: " + state);
		}
	}

	public State getGameState() {
		return getModel().getGameState();
	}

	/**
	 *
	 */
	public void resign() {
		getModel().resign();
	}

	/**
	 * @param source      Square
	 * @param destination Square
	 */
	public void makeMove(Square source, Square destination) {
		getModel().makeMove(source, destination);
	}

	/**
	 * @return boolean isCheckmate
	 */
	public boolean isCheckmate() {
		return getModel().isCheckmate();
	}

	/**
	 * @return boolean isStalemate
	 */
	public boolean isStalemate() {
		return getModel().isStalemate();
	}

	/**
	 * @return String piecePlacementData
	 */
	public String[] getPiecePlacementData() {
		return getModel().getPiecePlacementData();
	}

	/**
	 * @return char activeColor
	 */
	public char getActiveColor() {
		return getModel().getActiveColor();
	}

	/**
	 * @return boolean isWhite
	 */
	public boolean isWhite() {
		return getModel().isWhite();
	}

	/**
	 * @return String castlingInformation
	 */
	public String getCastling() {
		return getModel().getCastling();
	}

	/**
	 * @return boolean canWhiteKingCastle
	 */
	public boolean getWhiteKingCastle() {
		return getModel().getWhiteKingCastle();
	}

	/**
	 * @return boolean canWhiteQueenCastle
	 */
	public boolean getWhiteQueenCastle() {
		return getModel().getWhiteQueenCastle();
	}

	/**
	 * @return boolean canBlackKingCastle
	 */
	public boolean getBlackKingCastle() {
		return getModel().getBlackKingCastle();
	}

	/**
	 * @return boolean canBlackQueenCastle
	 */
	public boolean getBlackQueenCastle() {
		return getModel().getBlackQueenCastle();
	}

	/**
	 * @return String enPassantSquare
	 */
	public String getEnPassant() {
		return getModel().getEnPassant();
	}

	/**
	 * @return int halfMoveClock
	 */
	public int getHalfMoveClock() {
		return getModel().getHalfMoveClock();
	}

	/**
	 * @return int fullMoveNumber
	 */
	public int getFullMoveNumber() {
		return getModel().getFullMoveNumber();
	}

	public void handleUserInput(int x, int y) {
		State state = getGameState();
		switch (state) {
			case Start, GameOver -> System.out.println("handleStartGameOver");
			case Playing -> handlePlaying(x, y);
			case Checkmate -> System.out.println("handleCheckmate");
			case Stalemate -> System.out.println("handleStalemate");
			default -> throw new IllegalStateException("Unexpected value: " + state);
		}
	}

	private void handlePlaying(int x, int y) {
		System.out.println("Making move");
		Square square = getSquareFromCoordinates(x, y);
		if (square != null) {
			System.out.println("interacted on square " + square);
		} else {
			System.out.println("interacted outside board");
		}
	}

	private Square getSquareFromCoordinates(int x, int y) {
		boolean isOutsideHorizontally = isOutsideHorizontally(x);
		boolean isOutsideVertically = isOutsideVertically(y);
		boolean isOutside = isOutsideHorizontally || isOutsideVertically;
		if (isOutside) {
			return null;
		}
		int fileOffset = x - getView().getLeftBoardOffset();
		int rankOffset = (getView().getHeight() - getView().getTopBoardOffset()) - y;
		int squareSize = getView().getSquareSize();
		int file = getIndex(fileOffset, squareSize);
		int rank = getIndex(rankOffset, squareSize);
		return getSquareFromRankFile(rank, file);
	}

	private boolean isOutsideHorizontally(int point) {
		boolean isOutsideLeft = point < getView().getLeftBoardOffset();
		boolean isOutsideRight = point > getView().getWidth() - getView().getLeftBoardOffset();
		return isOutsideLeft || isOutsideRight;
	}

	private boolean isOutsideVertically(int point) {
		boolean isOutsideTop = point < getView().getTopBoardOffset();
		boolean isOutsideBottom = point > getView().getHeight() - getView().getTopBoardOffset();
		return isOutsideTop || isOutsideBottom;
	}

	private int getIndex(int offset, int squareSize) {
		return (int) Math.floor((double) offset / squareSize) + 1;
	}

	private Square getSquareFromRankFile(int rank, int file) {
		char rankChar = (char) (rank + '0');
		char fileChar = (char) (file + 'a' - 1);
		String square = new String(new char[] { fileChar, rankChar });
		return Square.valueOf(square);
	}

	private IChessModel getModel() {
		return model;
	}

	private IChessView getView() {
		return view;
	}
}

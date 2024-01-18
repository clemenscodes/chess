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

	public void handleUserInput(char key, int keyCode) {
		var state = getGameState();
		switch (state) {
			case Start, GameOver -> System.out.println("handleStartGameOver");
			case Playing -> System.out.println("handlePlaying");
			case Checkmate -> System.out.println("handleCheckmate");
			case Stalemate -> System.out.println("handleStalemate");
			default -> throw new IllegalStateException("Unexpected value: " + state);
		}
	}

	private IChessModel getModel() {
		return model;
	}

	private IChessView getView() {
		return view;
	}
}

package controller;

import api.controller.IChessController;
import api.model.IChessModel;
import api.model.Square;
import api.model.State;
import api.view.IChessView;
import java.util.ArrayList;

public class ChessController implements IChessController {

	private IChessModel model;
	private IChessView view;
	private Square source;
	private Square destination;
	private String errorMessage;

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
		if (getSource() != null) {
			System.out.println("Source: " + getSource());
		}
		if (getDestination() != null) {
			System.out.println("Destination: " + getDestination());
		}
		State state = getModel().getGameState();
		switch (state) {
			case Start -> getView().drawStart();
			case Playing -> getView().drawPlaying();
			case Checkmate -> getView().drawCheckmate();
			case Stalemate -> getView().drawStalemate();
			case Resignation -> getView().drawResignation();
			case Draw -> getView().drawDraw();
			default -> throw new IllegalStateException("Unexpected value: " + state);
		}
	}

	public State getGameState() {
		return getModel().getGameState();
	}

	public String getMoves() {
		return getModel().getMoves();
	}

	public ArrayList<Square[]> getLegalMoves(Square square) {
		return getModel().getLegalMoves(square);
	}

	/**
	 *
	 */
	public void resign() {
		getModel().resign();
	}

	public void offerDraw() {
		getModel().offerDraw();
	}

	/**
	 * @param source      Square
	 * @param destination Square
	 */
	public void makeMove(Square source, Square destination) {
		getModel().makeMove(source, destination);
	}

	/**
	 * @return String piecePlacementData
	 */
	public String[] getPiecePlacementData() {
		return getModel().getPiecePlacementData();
	}

	public String getFen() {
		return getModel().getFen();
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
		if (getGameState() == State.Playing) {
			handlePlaying(x, y);
		}
	}

	public void handleMousePressed(int x, int y) {
		if (getGameState() == State.Playing) {
			Square square = getSquareFromCoordinates(x, y);
			if (square == null) {
				return;
			}
			setSource(square);
		}
	}

	public Square getSource() {
		return source;
	}

	public Square getDestination() {
		return destination;
	}

	private void handlePlaying(int x, int y) {
		Square square = getSquareFromCoordinates(x, y);
		if (getSource() == null || square == null || square.equals(getSource())) {
			return;
		}
		setDestination(square);
		try {
			getModel().makeMove(getSource(), getDestination());
			clearErrorMessage();
		} catch (Error e) {
			setErrorMessage(e.getMessage());
		} finally {
			setSource(null);
			setDestination(null);
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

	private void setSource(Square source) {
		this.source = source;
	}

	private void setDestination(Square destination) {
		this.destination = destination;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void clearErrorMessage() {
		setSource(null);
		setDestination(null);
		setErrorMessage(null);
	}

	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}

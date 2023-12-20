package model;

import model.enums.GameState;
import model.fen.ForsythEdwardsNotation;
import model.piece.Piece;

public interface IChessModel {
	GameState getGameState();

	void startGame();

	void startNewGame();

	Piece[] getPieces();

	ForsythEdwardsNotation getFen();
}

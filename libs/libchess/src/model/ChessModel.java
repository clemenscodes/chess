package model;

import java.util.ArrayList;
import model.enums.GameState;
import model.piece.Piece;
import model.piece.extension.Empty;
import model.player.Player;
import model.player.extension.Black;
import model.player.extension.White;

public class ChessModel implements IChessModel {

	public static int FILES = 8;
	public static int RANKS = 8;
	private GameState state;
	private White white;
	private Black black;
	private Piece[] pieces;

	public void startGame(int width, int height) {
		setGameState(GameState.Start);
		setWhite(new White());
		setBlack(new Black());
		initializePieces();
	}

	public void startNewGame(int width, int height) {
		setGameState(GameState.Playing);
	}

	public GameState getGameState() {
		return state;
	}

	private void setGameState(GameState state) {
		this.state = state;
	}

	public static int getRankIndex(int rank) {
		if (rank >= 1 && rank <= RANKS) {
			return (rank - 1) * FILES;
		}
		throw new Error("Rank does not exist");
	}

	public White getWhite() {
		return white;
	}

	public Black getBlack() {
		return black;
	}

	private void setWhite(White white) {
		this.white = white;
	}

	private void setBlack(Black black) {
		this.black = black;
	}

	private void initializePieces() {
		pieces = new Piece[RANKS * FILES];
		for (int i = 0; i < pieces.length; i++) {
			pieces[i] = new Empty(i, i);
		}
		renderPieces(getWhite(), pieces);
		renderPieces(getBlack(), pieces);
	}

	private void renderPieces(Player player, Piece[] pieces) {
		var allPieces = new ArrayList<Piece>();
		allPieces.addAll(player.getPawns());
		allPieces.addAll(player.getBishops());
		allPieces.addAll(player.getKnights());
		allPieces.addAll(player.getRooks());
		allPieces.addAll(player.getQueens());
		allPieces.add(player.getKing());
		for (var p : allPieces) {
			pieces[p.getPosition()] = p;
		}
	}

	public void printBoard() {
		var boardString = new StringBuilder();
		for (int rank = RANKS - 1; rank >= 0; rank--) {
			for (int file = 0; file < FILES; file++) {
				int index = rank * FILES + file;
				Piece piece = pieces[index];
				char symbol = piece.getSymbol();
				boardString.append("[").append(symbol).append("]");
			}
			boardString.append("\n");
		}
		System.out.println(boardString);
	}
}

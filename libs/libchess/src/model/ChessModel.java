package model;

import java.util.ArrayList;
import model.enums.Color;
import model.enums.GameState;
import model.piece.Piece;
import model.piece.extension.Empty;
import model.player.Player;

public class ChessModel implements IChessModel {

	public static int FILES = 8;
	public static int RANKS = 8;
	private Piece[] pieces;
	private GameState state;
	private Player white;
	private Player black;

	public static void main(String[] args) {
		ChessModel chessModel = new ChessModel();
		chessModel.startGame();
		chessModel.printBoard();
	}

	public static int getRankIndex(int rank) {
		if (rank >= 1 && rank <= RANKS) {
			return (rank - 1) * FILES;
		}
		throw new Error("Rank does not exist");
	}

	public Piece[] getPieces() {
		return pieces;
	}

	public GameState getGameState() {
		return state;
	}

	public void startGame() {
		setGameState(GameState.Start);
		setWhite(new Player(Color.White));
		setBlack(new Player(Color.Black));
		initializePieces();
	}

	public void startNewGame() {
		setGameState(GameState.Playing);
	}

	private void setGameState(GameState state) {
		this.state = state;
	}

	public Player getWhite() {
		return white;
	}

	public Player getBlack() {
		return black;
	}

	private void setWhite(Player white) {
		this.white = white;
	}

	private void setBlack(Player black) {
		this.black = black;
	}

	private void initializePieces() {
		pieces = new Piece[RANKS * FILES];
		for (int i = 0; i < pieces.length; i++) {
			pieces[i] = new Empty(i, i);
		}
		addPieces(getWhite(), pieces);
		addPieces(getBlack(), pieces);
	}

	private void addPieces(Player player, Piece[] pieces) {
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

	private void printBoard() {
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

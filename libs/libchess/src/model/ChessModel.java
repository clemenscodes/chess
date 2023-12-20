package model;

import java.util.ArrayList;
import java.util.Arrays;
import model.enums.GameState;
import model.fen.ForsythEdwardsNotation;
import model.piece.Piece;
import model.player.Player;

public class ChessModel implements IChessModel {

	public static int FILES = 8;
	public static int RANKS = 8;
	private Piece[] pieces;
	private GameState state;
	private Player white;
	private Player black;
	private ForsythEdwardsNotation fen;
	private boolean next;

	public static void main(String[] args) {
		var model = new ChessModel();
		var fen = new ForsythEdwardsNotation();
		model.setFen(fen);
		model.startGame();
		model.move(10, 22);
		model.move(50, 34);
	}

	public static int getRankIndex(int rank) {
		if (rank >= 1 && rank <= RANKS) {
			return (rank - 1) * FILES;
		}
		throw new Error("Rank does not exist");
	}

	public ForsythEdwardsNotation getFen() {
		return fen;
	}

	public Piece[] getPieces() {
		return pieces;
	}

	public GameState getGameState() {
		return state;
	}

	public void startGame() {
		setGameState(GameState.Start);
		setWhite(new Player(true));
		setBlack(new Player(false));
		initializePieces();
		initializeNext();
		printGame();
	}

	public void startNewGame() {
		startGame();
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

	private void setFen(ForsythEdwardsNotation fen) {
		this.fen = fen;
	}

	private void setNext() {
		next = !getNext();
		printNext();
	}

	private boolean getNext() {
		return next;
	}

	private void initializeNext() {
		next = true;
	}

	private void initializePieces() {
		pieces = new Piece[RANKS * FILES];
		Arrays.fill(pieces, null);
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
				char symbol = piece == null ? ' ' : piece.getSymbol();
				boardString.append("[").append(symbol).append("]");
			}
			if (rank != 0) boardString.append("\n");
		}
		System.out.println(boardString);
	}

	private void printNext() {
		System.out.print("Next turn: ");
		System.out.println(getNext() ? "White" : "Black");
	}

	private void printGame() {
		printBoard();
		printNext();
	}

	private void move(String algebraicMove) {
		System.out.println(
			(getNext() ? "White" : "Black") + "'s move: " + algebraicMove
		);
	}

	private void move(int source, int destination) {
		var p = pieces[source];
		if (!p.isValidMove(destination, pieces)) {
			throw new Error("Invalid move");
		}
		pieces[source] = null;
		pieces[destination] = p;
		printBoard();
		setNext();
	}
}

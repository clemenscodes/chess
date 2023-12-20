package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.enums.GameState;
import model.piece.Piece;
import model.player.Player;

public class ChessModel implements IChessModel {

	public static int FILES = 8;
	public static int RANKS = 8;
	private Piece[] pieces;
	private GameState state;
	private Player white;
	private Player black;
	private String[] piecePlacementData;
	private char activeColor;
	private String castling;
	private String enPassant;
	private int halfMoveClock;
	private int fullMoveNumber;

	public static void main(String[] args) {
		var model = new ChessModel();
		model.startGame();
	}

	public static int getRankIndex(int rank) {
		if (rank >= 1 && rank <= RANKS) {
			return (rank - 1) * FILES;
		}
		throw new Error("Rank does not exist");
	}

	public String[] getPiecePlacementData() {
		return piecePlacementData;
	}

	public char getActiveColor() {
		return activeColor;
	}

	public String getCastling() {
		return castling;
	}

	public String getEnPassant() {
		return enPassant;
	}

	public int getHalfMoveClock() {
		return halfMoveClock;
	}

	public int getFullMoveNumber() {
		return fullMoveNumber;
	}

	public GameState getGameState() {
		return state;
	}

	public void startGame() {
		setGameState(GameState.Start);
		setWhite(new Player(true));
		setBlack(new Player(false));
		parseFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		initializePieces();
		printGame();
	}

	public void startNewGame() {
		startGame();
		setGameState(GameState.Playing);
	}

	public Piece[] getPieces() {
		return pieces;
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
		System.out.println(getActiveColor() == 'w' ? "White" : "Black");
	}

	private void printGame() {
		printBoard();
		printNext();
	}

	private void move(String algebraicMove) {
		System.out.println(
			(getActiveColor() == 'w' ? "White" : "Black") +
			"'s move: " +
			algebraicMove
		);
	}

	private void move(int source, int destination) {
		var p = pieces[source];
		if (!p.isValidMove(destination, pieces)) {
			throw new Error("Invalid move");
		}
		pieces[source] = null;
		pieces[destination] = p;
		setActiveColor(getActiveColor() == 'w' ? "b" : "w");
		printBoard();
	}

	private void parseFen(String fen) {
		String[] parts = fen.split(" ");
		if (parts.length != 6) {
			throw new IllegalArgumentException(
				"Invalid FEN: It should consist of 6 space-separated parts"
			);
		}
		setPiecePlacementData(parts[0]);
		setActiveColor(parts[1]);
		setCastling(parts[2]);
		setEnPassant(parts[3]);
		setHalfMoveClock(parts[4]);
		setFullMoveNumber(parts[5]);
	}

	private void setPiecePlacementData(String piecePlacement) {
		var ppd = piecePlacement.split("/");
		if (ppd.length != ChessModel.RANKS) {
			throw new IllegalArgumentException("Invalid piece placement data");
		}
		for (var rank : ppd) {
			if (rank.length() > ChessModel.FILES) {
				throw new IllegalArgumentException(
					"Invalid piece placement data: Each rank should have at most " +
					ChessModel.FILES +
					" files"
				);
			}
		}
		piecePlacementData = ppd;
	}

	private void setActiveColor(String color) {
		try {
			var ac = color.charAt(0);
			if (ac != 'w' && ac != 'b') {
				throw new IllegalArgumentException(
					"Active color must be either 'w' or 'b'"
				);
			}
			activeColor = ac;
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid active color");
		}
	}

	private void setCastling(String castlingInfo) {
		try {
			if (!castlingInfo.equals("-")) {
				for (char c : castlingInfo.toCharArray()) {
					if (c != 'K' && c != 'Q' && c != 'k' && c != 'q') {
						throw new IllegalArgumentException(
							"Invalid castling information: Use 'K', 'Q', 'k', 'q', or '-'"
						);
					}
				}
			}
			castling = castlingInfo;
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid castling information");
		}
	}

	private void setEnPassant(String enPassantInfo) {
		try {
			if (!enPassantInfo.equals("-")) {
				if (
					enPassantInfo.length() != 2 ||
					!isValidEnPassantSquare(enPassantInfo)
				) {
					throw new IllegalArgumentException(
						"Invalid en passant target square"
					);
				}
			}
			enPassant = enPassantInfo;
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException(
				"Invalid en passant information"
			);
		}
	}

	private boolean isValidEnPassantSquare(String square) {
		char file = square.charAt(0);
		char rank = square.charAt(1);
		return (file >= 'a' && file <= 'h') && ((rank == '3') || (rank == '6'));
	}

	private void setHalfMoveClock(String halfMoveClockStr) {
		try {
			int halfMoveClockValue = Integer.parseInt(halfMoveClockStr);
			if (halfMoveClockValue < 0) {
				throw new IllegalArgumentException(
					"Invalid half-move clock: It cannot be negative"
				);
			}
			if (halfMoveClockValue > 150) {
				throw new IllegalArgumentException(
					"Invalid half-move clock: Maximum allowed value is 150 under the seventy-five move rule"
				);
			}
			halfMoveClock = halfMoveClockValue;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid half-move clock");
		}
	}

	private void setFullMoveNumber(String fullMoveNumberStr) {
		try {
			int fullMoveNumberValue = Integer.parseInt(fullMoveNumberStr);
			if (fullMoveNumberValue < 1) {
				throw new IllegalArgumentException(
					"Invalid full-move number: It must be greater than or equal to 1"
				);
			}
			fullMoveNumber = fullMoveNumberValue;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid full-move number");
		}
	}

	public static class ChessMoveParser {

		public static Move parseAlgebraicNotation(String algebraicNotation) {
			Pattern pattern = Pattern.compile(
				"([NBRQK]?[a-h]?[1-8]?[x-]?)?([a-h][1-8])(=[NBRQK])?([+#])?"
			);
			Matcher matcher = pattern.matcher(algebraicNotation);

			if (matcher.matches()) {
				String piece = matcher.group(1);
				String destinationSquare = matcher.group(2);
				String promotion = matcher.group(3);
				String checkOrMate = matcher.group(4);
				System.out.println("Piece: " + piece);
				System.out.println("Destination Square: " + destinationSquare);
				System.out.println("Promotion: " + promotion);
				System.out.println("Check or Mate: " + checkOrMate);
				return new Move(
					piece,
					destinationSquare,
					promotion,
					checkOrMate
				);
			} else {
				System.out.println(
					"Invalid algebraic notation. Please provide a valid move."
				);
				return null;
			}
		}

		public static void main(String[] args) {
			String algebraicNotation = "Nf3xNd2#";
			Move parsedMove = parseAlgebraicNotation(algebraicNotation);
			if (parsedMove != null) {
				System.out.println("Parsed Move: " + parsedMove.toString());
			}
		}
	}

	public static class Move {

		private String piece;
		private String destinationSquare;
		private String promotion;
		private String checkOrMate;

		public Move(
			String piece,
			String destinationSquare,
			String promotion,
			String checkOrMate
		) {
			this.piece = piece;
			this.destinationSquare = destinationSquare;
			this.promotion = promotion;
			this.checkOrMate = checkOrMate;
		}

		@Override
		public String toString() {
			return (
				"Move: " +
				"Piece='" +
				piece +
				'\'' +
				", Destination Square='" +
				destinationSquare +
				'\'' +
				", Promotion='" +
				promotion +
				'\'' +
				", Check or Mate='" +
				checkOrMate +
				'\''
			);
		}
	}
}

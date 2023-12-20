package model.board;

import java.io.Serializable;
import java.util.ArrayList;
import model.piece.Empty;
import model.piece.Piece;
import model.player.Black;
import model.player.Player;
import model.player.White;

public class Board implements Serializable {

	public static int COLUMNS = 8;
	public static int ROWS = 8;
	public static int A_ROW = 0;
	public static int B_ROW = 8;
	public static int C_ROW = 16;
	public static int D_ROW = 24;
	public static int E_ROW = 32;
	public static int F_ROW = 40;
	public static int G_ROW = 48;
	public static int H_ROW = 56;
	private Piece[] pieces;
	private White white;
	private Black black;

	public static int getRowIndex(int row) {
		if (row >= 1 && row <= ROWS) {
			return (row - 1) * COLUMNS;
		}
		throw new Error("Row does not exist");
	}

	public Board() {
		setWhite(new White());
		setBlack(new Black());
		initializePieces();
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
		pieces = new Piece[ROWS * COLUMNS];
		for (int i = 0; i < 64; i++) {
			pieces[i] = new Empty(i, i);
		}
		renderPieces(getWhite(), pieces);
		renderPieces(getBlack(), pieces);
	}

	private void renderPieces(Player player, Piece[] pieces) {
		ArrayList<Piece> allPieces = new ArrayList<>();
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

	@Override
	public String toString() {
		StringBuilder boardString = new StringBuilder();
		for (int row = ROWS - 1; row >= 0; row--) {
			for (int col = 0; col < COLUMNS; col++) {
				int index = row * COLUMNS + col;
				Piece piece = pieces[index];
				char symbol = piece.getSymbol();
				boardString.append("[").append(symbol).append("]");
			}
			boardString.append("\n");
		}
		return boardString.toString();
	}
}

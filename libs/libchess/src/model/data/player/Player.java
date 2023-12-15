package model.data.player;

import java.io.Serializable;
import java.util.ArrayList;
import model.data.Color;
import model.data.board.Board;
import model.data.piece.*;

public class Player implements Serializable {

	private Color color;
	private ArrayList<Pawn> pawns;
	private ArrayList<Bishop> bishops;
	private ArrayList<Knight> knights;
	private ArrayList<Rook> rooks;
	private ArrayList<Queen> queens;
	private King king;

	public Player(Color color) {
		setColor(color);
		initializePawns();
		initializeKnights();
		initializeBishops();
		initializeRooks();
		initializeQueens();
		initializeKing();
	}

	private void initializePawns() {
		pawns = new ArrayList<>();
		for (int i = 1; i <= 8; i++) {
			int position = getColor() == Color.WHITE
				? Board.getRowIndex(2) + i
				: Board.getRowIndex(8) - i;
			pawns.add(new Pawn(getColor(), position, position));
		}
	}

	private void initializeKnights() {
		int position;
		knights = new ArrayList<>();
		if (getColor() == Color.WHITE) {
			position = Board.getRowIndex(1) + 2;
			knights.add(new Knight(getColor(), position, position));
			position = Board.getRowIndex(1) + 7;
			knights.add(new Knight(getColor(), position, position));
		} else {
			position = Board.getRowIndex(8) + 2;
			knights.add(new Knight(getColor(), position, position));
			position = Board.getRowIndex(8) + 7;
			knights.add(new Knight(getColor(), position, position));
		}
	}

	private void initializeBishops() {
		int position;
		bishops = new ArrayList<>();
		if (getColor() == Color.WHITE) {
			position = Board.getRowIndex(1) + 3;
			bishops.add(new Bishop(getColor(), position, position));
			position = Board.getRowIndex(1) + 6;
			bishops.add(new Bishop(getColor(), position, position));
		} else {
			position = Board.getRowIndex(8) + 3;
			bishops.add(new Bishop(getColor(), position, position));
			position = Board.getRowIndex(8) + 6;
			bishops.add(new Bishop(getColor(), position, position));
		}
	}

	private void initializeRooks() {
		int position;
		rooks = new ArrayList<>();
		if (getColor() == Color.WHITE) {
			position = Board.getRowIndex(1) + 1;
			rooks.add(new Rook(getColor(), position, position));
			position = Board.getRowIndex(1) + 8;
			rooks.add(new Rook(getColor(), position, position));
		} else {
			position = Board.getRowIndex(8) + 1;
			rooks.add(new Rook(getColor(), position, position));
			position = Board.getRowIndex(8) + 8;
			rooks.add(new Rook(getColor(), position, position));
		}
	}

	private void initializeQueens() {
		queens = new ArrayList<>();
		int position = getColor() == Color.WHITE
			? Board.getRowIndex(1) + 4
			: Board.getRowIndex(8) + 4;
		queens.add(new Queen(getColor(), position, position));
	}

	private void initializeKing() {
		int position = getColor() == Color.WHITE
			? Board.getRowIndex(1) + 5
			: Board.getRowIndex(8) + 5;
		king = new King(getColor(), position, position);
	}

	public ArrayList<Pawn> getPawns() {
		return pawns;
	}

	public ArrayList<Knight> getKnights() {
		return knights;
	}

	public ArrayList<Bishop> getBishops() {
		return bishops;
	}

	public ArrayList<Rook> getRooks() {
		return rooks;
	}

	public ArrayList<Queen> getQueens() {
		return queens;
	}

	public King getKing() {
		return king;
	}

	public Color getColor() {
		return color;
	}

	private void setColor(Color color) {
		this.color = color;
	}

	public void setPawns(ArrayList<Pawn> pawns) {
		this.pawns = pawns;
	}

	public void setKnights(ArrayList<Knight> knights) {
		this.knights = knights;
	}

	public void setBishops(ArrayList<Bishop> bishops) {
		this.bishops = bishops;
	}

	public void setRooks(ArrayList<Rook> rooks) {
		this.rooks = rooks;
	}

	public void setQueens(ArrayList<Queen> queens) {
		this.queens = queens;
	}

	public void setKing(King king) {
		this.king = king;
	}
}

package model.player;

import java.io.Serializable;
import java.util.ArrayList;
import model.ChessModel;
import model.enums.Color;
import model.piece.extension.*;

public class Player implements IPlayer, Serializable {

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

	private void initializePawns() {
		ArrayList<Pawn> pawns = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			int position = getColor() == Color.White
				? ChessModel.getRankIndex(2) + i
				: ChessModel.getRankIndex(8) - i - 1;
			pawns.add(new Pawn(getColor(), position, position));
		}
		setPawns(pawns);
	}

	private void initializeKnights() {
		int position;
		ArrayList<Knight> knights = new ArrayList<>();
		if (getColor() == Color.White) {
			position = ChessModel.getRankIndex(1) + 1;
			knights.add(new Knight(getColor(), position, position));
			position = ChessModel.getRankIndex(1) + 6;
			knights.add(new Knight(getColor(), position, position));
		} else {
			position = ChessModel.getRankIndex(8) + 1;
			knights.add(new Knight(getColor(), position, position));
			position = ChessModel.getRankIndex(8) + 6;
			knights.add(new Knight(getColor(), position, position));
		}
		setKnights(knights);
	}

	private void initializeBishops() {
		int position;
		ArrayList<Bishop> bishops = new ArrayList<>();
		if (getColor() == Color.White) {
			position = ChessModel.getRankIndex(1) + 2;
			bishops.add(new Bishop(getColor(), position, position));
			position = ChessModel.getRankIndex(1) + 5;
			bishops.add(new Bishop(getColor(), position, position));
		} else {
			position = ChessModel.getRankIndex(8) + 2;
			bishops.add(new Bishop(getColor(), position, position));
			position = ChessModel.getRankIndex(8) + 5;
			bishops.add(new Bishop(getColor(), position, position));
		}
		setBishops(bishops);
	}

	private void initializeRooks() {
		int position;
		ArrayList<Rook> rooks = new ArrayList<>();
		if (getColor() == Color.White) {
			position = ChessModel.getRankIndex(1);
			rooks.add(new Rook(getColor(), position, position));
			position = ChessModel.getRankIndex(1) + 7;
			rooks.add(new Rook(getColor(), position, position));
		} else {
			position = ChessModel.getRankIndex(8);
			rooks.add(new Rook(getColor(), position, position));
			position = ChessModel.getRankIndex(8) + 7;
			rooks.add(new Rook(getColor(), position, position));
		}
		setRooks(rooks);
	}

	private void initializeQueens() {
		ArrayList<Queen> queens = new ArrayList<>();
		int position = getColor() == Color.White
			? ChessModel.getRankIndex(1) + 3
			: ChessModel.getRankIndex(8) + 3;
		queens.add(new Queen(getColor(), position, position));
		setQueens(queens);
	}

	private void initializeKing() {
		int position = getColor() == Color.White
			? ChessModel.getRankIndex(1) + 4
			: ChessModel.getRankIndex(8) + 4;
		King king = new King(getColor(), position, position);
		setKing(king);
	}
}

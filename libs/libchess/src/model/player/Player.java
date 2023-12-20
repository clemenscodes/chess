package model.player;

import java.io.Serializable;
import java.util.ArrayList;
import model.ChessModel;
import model.piece.extension.*;

public class Player implements IPlayer, Serializable {

	private boolean isWhite;
	private ArrayList<Pawn> pawns;
	private ArrayList<Bishop> bishops;
	private ArrayList<Knight> knights;
	private ArrayList<Rook> rooks;
	private ArrayList<Queen> queens;
	private King king;

	public Player(boolean isWhite) {
		setIsWhite(isWhite);
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

	public boolean isWhite() {
		return isWhite;
	}

	private void setIsWhite(boolean isWhite) {
		this.isWhite = isWhite;
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
			int position = isWhite()
				? ChessModel.getRankIndex(2) + i
				: ChessModel.getRankIndex(8) - i - 1;
			pawns.add(new Pawn(isWhite(), position));
		}
		setPawns(pawns);
	}

	private void initializeKnights() {
		int position;
		ArrayList<Knight> knights = new ArrayList<>();
		if (isWhite()) {
			position = ChessModel.getRankIndex(1) + 1;
			knights.add(new Knight(isWhite(), position));
			position = ChessModel.getRankIndex(1) + 6;
			knights.add(new Knight(isWhite(), position));
		} else {
			position = ChessModel.getRankIndex(8) + 1;
			knights.add(new Knight(isWhite(), position));
			position = ChessModel.getRankIndex(8) + 6;
			knights.add(new Knight(isWhite(), position));
		}
		setKnights(knights);
	}

	private void initializeBishops() {
		int position;
		ArrayList<Bishop> bishops = new ArrayList<>();
		if (isWhite()) {
			position = ChessModel.getRankIndex(1) + 2;
			bishops.add(new Bishop(isWhite(), position));
			position = ChessModel.getRankIndex(1) + 5;
			bishops.add(new Bishop(isWhite(), position));
		} else {
			position = ChessModel.getRankIndex(8) + 2;
			bishops.add(new Bishop(isWhite(), position));
			position = ChessModel.getRankIndex(8) + 5;
			bishops.add(new Bishop(isWhite(), position));
		}
		setBishops(bishops);
	}

	private void initializeRooks() {
		int position;
		ArrayList<Rook> rooks = new ArrayList<>();
		if (isWhite()) {
			position = ChessModel.getRankIndex(1);
			rooks.add(new Rook(isWhite(), position));
			position = ChessModel.getRankIndex(1) + 7;
			rooks.add(new Rook(isWhite(), position));
		} else {
			position = ChessModel.getRankIndex(8);
			rooks.add(new Rook(isWhite(), position));
			position = ChessModel.getRankIndex(8) + 7;
			rooks.add(new Rook(isWhite(), position));
		}
		setRooks(rooks);
	}

	private void initializeQueens() {
		ArrayList<Queen> queens = new ArrayList<>();
		int position = isWhite()
			? ChessModel.getRankIndex(1) + 3
			: ChessModel.getRankIndex(8) + 3;
		queens.add(new Queen(isWhite(), position));
		setQueens(queens);
	}

	private void initializeKing() {
		int position = isWhite()
			? ChessModel.getRankIndex(1) + 4
			: ChessModel.getRankIndex(8) + 4;
		King king = new King(isWhite(), position);
		setKing(king);
	}
}

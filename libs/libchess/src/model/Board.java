package model;

import java.io.Serializable;
import model.piece.bishop.extension.BlackBishop;
import model.piece.bishop.extension.WhiteBishop;
import model.piece.king.extension.BlackKing;
import model.piece.king.extension.WhiteKing;
import model.piece.knight.extension.BlackKnight;
import model.piece.knight.extension.WhiteKnight;
import model.piece.pawn.extension.BlackPawn;
import model.piece.pawn.extension.WhitePawn;
import model.piece.queen.extension.BlackQueen;
import model.piece.queen.extension.WhiteQueen;
import model.piece.rook.extension.BlackRook;
import model.piece.rook.extension.WhiteRook;

public class Board implements Serializable {

    public static final int DIMENSION = 8;
	private WhiteKing whiteKing;
	private WhiteQueen whiteQueen;
	private WhiteRook whiteRook;
	private WhiteKnight whiteKnight;
	private WhiteBishop whiteBishop;
	private WhitePawn whitePawn;
	private BlackKing blackKing;
	private BlackQueen blackQueen;
	private BlackRook blackRook;
	private BlackKnight blackKnight;
	private BlackBishop blackBishop;
	private BlackPawn blackPawn;

	public WhiteKing getWhiteKing() {
		return whiteKing;
	}

	public void setWhiteKing(WhiteKing whiteKing) {
		this.whiteKing = whiteKing;
	}

	public WhiteQueen getWhiteQueen() {
		return whiteQueen;
	}

	public void setWhiteQueen(WhiteQueen whiteQueen) {
		this.whiteQueen = whiteQueen;
	}

	public WhiteRook getWhiteRook() {
		return whiteRook;
	}

	public void setWhiteRook(WhiteRook whiteRook) {
		this.whiteRook = whiteRook;
	}

	public WhiteKnight getWhiteKnight() {
		return whiteKnight;
	}

	public void setWhiteKnight(WhiteKnight whiteKnight) {
		this.whiteKnight = whiteKnight;
	}

	public WhiteBishop getWhiteBishop() {
		return whiteBishop;
	}

	public void setWhiteBishop(WhiteBishop whiteBishop) {
		this.whiteBishop = whiteBishop;
	}

	public WhitePawn getWhitePawn() {
		return whitePawn;
	}

	public void setWhitePawn(WhitePawn whitePawn) {
		this.whitePawn = whitePawn;
	}

	public BlackKing getBlackKing() {
		return blackKing;
	}

	public void setBlackKing(BlackKing blackKing) {
		this.blackKing = blackKing;
	}

	public BlackQueen getBlackQueen() {
		return blackQueen;
	}

	public void setBlackQueen(BlackQueen blackQueen) {
		this.blackQueen = blackQueen;
	}

	public BlackRook getBlackRook() {
		return blackRook;
	}

	public void setBlackRook(BlackRook blackRook) {
		this.blackRook = blackRook;
	}

	public BlackKnight getBlackKnight() {
		return blackKnight;
	}

	public void setBlackKnight(BlackKnight blackKnight) {
		this.blackKnight = blackKnight;
	}

	public BlackBishop getBlackBishop() {
		return blackBishop;
	}

	public void setBlackBishop(BlackBishop blackBishop) {
		this.blackBishop = blackBishop;
	}

	public BlackPawn getBlackPawn() {
		return blackPawn;
	}

	public void setBlackPawn(BlackPawn blackPawn) {
		this.blackPawn = blackPawn;
	}
}

package model.board;

import model.bits.IBitboard;
import model.piece.IPiece;
import model.piece.Pieces;
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

public interface IBoard {
	WhiteKing getWhiteKing();

	WhiteQueen getWhiteQueen();

	WhiteRook getWhiteRook();

	WhiteKnight getWhiteKnight();

	WhiteBishop getWhiteBishop();

	WhitePawn getWhitePawn();

	BlackKing getBlackKing();

	BlackQueen getBlackQueen();

	BlackRook getBlackRook();

	BlackKnight getBlackKnight();

	BlackBishop getBlackBishop();

	BlackPawn getBlackPawn();

	IBitboard getWhitePieces();

	IBitboard getBlackPieces();

	IBitboard getOccupiedSquares();

	IBitboard getEmptySquares();

	IBitboard[] getAllPieces();
	Pieces getPieceByIndex(int index);
	IPiece getPiece(Pieces kind);

	void setPieces(String[] ppd);
}
